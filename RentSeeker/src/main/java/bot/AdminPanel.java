package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import payload.LanStateDTO;
import service.BotService;
import service.UserService;
import service.adminService.AdminBotService;
import util.constant.Constant;
import util.enums.BotState;
import util.enums.Language;
import util.enums.Role;
import util.security.BaseData;

import java.util.List;

import static util.enums.BotState.*;

public class AdminPanel extends TelegramLongPollingBot implements Constant {

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        LanStateDTO data = UserService.getAndCheck(update);
        BotState state = data.getState();
        Language lan = data.getLanguage();
        Role role=data.getRole();
        boolean isAdmin = data.isAdmin();
        if (update.hasMessage()){
            Message message = update.getMessage();
            if(message.hasText()){
                String text = message.getText();
                if(text.equals(getBotToken())){
                    state = ADMIN_MAIN_MENU_SEND;
                }else if(state == BotState.ADMIN_USER_ENTER_PHONE_NUMBER){
                    if (UserService.phoneNumberValidation(text)){
                        if(UserService.checkByPhoneNumber(text)){
                            state = ADMIN_SEARCH_USER_INFO_SEND;
                        }else{
                            state = BotState.ERROR;
                        }
                    }
                }
            }
        }else if (update.hasCallbackQuery()){

            switch (update.getCallbackQuery().getData()){
                case ADMIN,BACK_TO_ADMIN_MAIN_MENU -> state = BotState.ADMIN_MAIN_MENU_EDIT;
                case ADMIN_SHOW_USERS,BACK_TO_ADMIN_USERS_SHOW -> state = BotState.ADMIN_USERS_SHOW;
                case BACK_TO_ADMIN_CHOOSE_USER_TYPE -> state = ADMIN_CHOOSE_USER_TYPE_SEND;
                case ADMIN_EXCEL_FILE -> state= ADMIN_CHOOSE_USER_TYPE_EDIT;
                case ADMIN_ACTIVE_USERS -> state = BotState.ADMIN_GET_ACTIVE_EXCEL_FILE;
                case ADMIN_DEACTIVATED_USERS -> state = BotState.ADMIN_GET_DEACTIVATED_EXCEL_FILE;
                case SEARCH -> state = BotState.ADMIN_USER_ENTER_PHONE_NUMBER;
                case ADMIN_SHOW_HOMES,BACK_TO_ADMIN_HOMES_FILTER -> state = BotState.ADMIN_HOMES_FILTER;
                case ADMIN_HOMES_IN_WEEK -> state = ADMIN_WEEK_HOMES_INFO;
                case ADMIN_HOMES_IN_DAY -> state = ADMIN_DAY_HOMES_INFO;
                case BACK_TO_ADMIN_MENU -> state= ADMIN_MENU_EDIT;
            }
            if (!update.getCallbackQuery().getData().equals(BACK_TO_ADMIN_USERS_SHOW)&&
                    state==ADMIN_SEARCH_USER_INFO_SEND){
                state = ADMIN_SEARCH_USER_INFO_EDIT;
            }
        }
        switch (state){
            case ADMIN_MENU_EDIT -> {
                execute(BotService.getAdminMenuEdit(update,lan));
                BotService.changeIsAdminUser(update,false);
            }
            case ADMIN_MAIN_MENU_SEND-> execute(AdminBotService.setAdminMenuSend(update,lan));
            case ADMIN_MAIN_MENU_EDIT -> execute(AdminBotService.setAdminMenuEdit(update,lan));
            case ADMIN_USERS_SHOW -> execute(AdminBotService.setAdminShowUsersEdit(update,lan));
            case ADMIN_CHOOSE_USER_TYPE_SEND -> execute(AdminBotService.setAdminChooseUsersSend(update,lan));
            case ADMIN_CHOOSE_USER_TYPE_EDIT -> execute(AdminBotService.setAdminChooseUsersEdit(update,lan));
            case ADMIN_USER_ENTER_PHONE_NUMBER -> execute(AdminBotService.setAdminUserEnterPhoneEdit(update,lan));
            case ADMIN_HOMES_FILTER -> execute(AdminBotService.setAdminHomeFilterEdit(update,lan));
            case ADMIN_GET_ACTIVE_EXCEL_FILE -> {
                execute(BotService.deleteMessage(update));
                execute(AdminBotService.getExelFile(update,lan,true));
            }
            case ADMIN_GET_DEACTIVATED_EXCEL_FILE -> {
                execute(BotService.deleteMessage(update));
                execute(AdminBotService.getExelFile(update,lan,false));
            }
            case ADMIN_SEARCH_USER_INFO_SEND -> execute(AdminBotService.sendAdminUserInfo(update,lan));
            case ADMIN_SEARCH_USER_INFO_EDIT -> execute(AdminBotService.editAdminUserInfo(update,lan));
            case ADMIN_DAY_HOMES_INFO -> {
                showHomes(update,lan,ADMIN_HOMES_IN_DAY);
                state = ADMIN_HOMES_INFO;
            }
            case ADMIN_WEEK_HOMES_INFO ->{
                showHomes(update,lan,ADMIN_HOMES_IN_WEEK);
                state = ADMIN_HOMES_INFO;
            }
            case ADMIN_HOMES_INFO ->{
                execute(AdminBotService.sendAdminDeleteHome(update,lan));

            }
            default -> execute(BotService.setError(update));
        }
        UserService.saveStateAndLan(update, new LanStateDTO(lan, state, role,isAdmin));
    }

    private void showHomes(Update update,Language lan,String searchType) throws TelegramApiException {
        List<SendPhoto> sendPhotos = AdminBotService.showHomes(update, lan, searchType);
            if (sendPhotos == null) {
                execute(BotService.deleteMessage(update));
                execute(BotService.homeNotFound(update, lan, BACK_TO_ADMIN_HOMES_FILTER));
                return;
        }
        for (SendPhoto sendPhoto : sendPhotos) {
            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BaseData.USERNAME;
    }

    @Override
    public String getBotToken() {
        return BaseData.TOKEN;
    }
}
