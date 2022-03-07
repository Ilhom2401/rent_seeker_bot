package bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import payload.LanStateDTO;
import service.*;
import util.constant.Constant;
import util.enums.BotState;
import util.enums.Language;
import util.enums.Role;
import util.security.BaseData;

import java.util.List;


public class RentSeeker extends TelegramLongPollingBot implements Constant {


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasCallbackQuery() && !update.hasMessage())
            return;
        LanStateDTO res = UserService.getAndCheck(update);
        BotState state = res.getState();
        Language lan = res.getLanguage();
        Role role = res.getRole();
        boolean isAdmin = res.isAdmin();

        if(update.hasMessage() && update.getMessage().hasText() && update.getMessage().getText().equals(START))isAdmin=false;
        if(isAdmin) {
            new AdminPanel().onUpdateReceived(update);
            return;
        }if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals(START)) {
                    if (!state.equals(BotState.CHOOSE_LANGUAGE)) {
                        state =role.equals(Role.ADMIN)?BotState.ADMIN_MENU_SEND:BotState.MAIN_MENU_SEND;
                        execute(BotService.removeKeyBoardMarkup(update));
                    }
                } else if (text.equals(BaseData.TOKEN))
                    state = BotState.ADMIN_MENU_SEND;
                else if (state.equals(BotState.REGISTER))
                    state = BotState.SETTINGS_MENU_SEND;
                else if (state.equals(BotState.LOCATION_MENU))
                    state = BotState.WRITE_OR_SEND_LOCATION;
                else if (state.equals(BotState.GIVE_ADDRESS))
                    state = BotState.GIVE_HOME_TYPE_SEND;
                else if (state.equals(BotState.GIVE_HOME_NUMBER))
                    state = BotState.GIVE_HOME_AREA_SEND;
                else if (state.equals(BotState.GIVE_HOME_AREA_SEND) || state.equals(BotState.GIVE_HOME_AREA_EDIT))
                    state = BotState.GIVE_HOME_PHOTO_SEND;
                else if (state.equals(BotState.GIVE_HOME_PRICE_SEND) || state.equals(BotState.GIVE_HOME_PRICE_EDIT))
                    state = BotState.GIVE_HOME_DESCRIPTION;
                else if (state.equals(BotState.GIVE_HOME_DESCRIPTION))
                    state = BotState.SAVE_HOME_TO_STORE;
                else if (state.equals(BotState.CHOOSE_HOME_NUMBER_EDIT) || state.equals(BotState.CHOOSE_HOME_NUMBER_SEND))
                    state = BotState.CHOOSE_HOME_MIN_PRICE_SEND;
                else if (state.equals(BotState.CHOOSE_HOME_MIN_PRICE_SEND) || state.equals(BotState.CHOOSE_HOME_MIN_PRICE_EDIT))
                    state = BotState.CHOOSE_HOME_MAX_PRICE_SEND;
                else if (state.equals(BotState.CHOOSE_HOME_MAX_PRICE_SEND) || state.equals(BotState.CHOOSE_HOME_MAX_PRICE_EDIT))
                    state = BotState.SHOW_SORTED_OPTIONS;
                else {
                    execute(BotService.setError(update));
                    return;
                }
            } else if (message.hasContact() && state.equals(BotState.REGISTER))
                state = BotState.SETTINGS_MENU_SEND;
            else if (message.hasLocation() && state.equals(BotState.LOCATION_MENU))
                state = BotState.GIVE_HOME_TYPE_SEND;
            else if (message.hasPhoto() && (state.equals(BotState.GIVE_HOME_PHOTO_SEND) || state.equals(BotState.GIVE_HOME_PHOTO_EDIT)))
                state = BotState.GIVE_HOME_PRICE_SEND;
            else {
                execute(BotService.setError(update));
                return;
            }
        } else if (update.hasCallbackQuery()) {
            switch (update.getCallbackQuery().getData()) {
                case UZ -> {
                    lan = Language.UZ;
                    state = state.equals(BotState.CHOOSE_LANGUAGE) ? BotState.MAIN_MENU_EDIT : BotState.SETTINGS_MENU_EDIT;
                }
                case RU -> {
                    lan = Language.RU;
                    state = state.equals(BotState.CHOOSE_LANGUAGE) ? BotState.MAIN_MENU_EDIT : BotState.SETTINGS_MENU_EDIT;
                }
                case EN -> {
                    lan = Language.EN;
                    state = state.equals(BotState.CHOOSE_LANGUAGE) ? BotState.MAIN_MENU_EDIT : BotState.SETTINGS_MENU_EDIT;
                }
                case ADMIN -> state = BotState.ADMIN_PANEL;
                case SETTINGS, BACK_TO_SETTINGS_MENU_EDIT -> state = BotState.SETTINGS_MENU_EDIT;
                case BACK_TO_ADMIN_MENU -> state = BotState.ADMIN_MENU_EDIT;
                case BACK_TO_SETTINGS_MENU_SEND -> state = BotState.SETTINGS_MENU_SEND;
                case BACK_TO_MAIN_MENU_SEND -> state = BotState.MAIN_MENU_SEND;
                case BACK_TO_MAIN_MENU_EDIT,USER -> state = BotState.MAIN_MENU_EDIT;
                case CHANGE_LANGUAGE -> state = BotState.CHANGE_LANGUAGE;
                case REGISTRATION -> state = BotState.REGISTER;
                case ADD_ACCOMMODATION, BACK_TO_GIVE_HOME_STATUS -> state = BotState.GIVE_HOME_STATUS;
                case BACK_TO_WRITE_SEND_LOCATION, FOR_RENTING, FOR_SELLING -> state = BotState.WRITE_OR_SEND_LOCATION;
                case BACK_SEND_LOCATION, SEND_LOCATION -> state = BotState.LOCATION_MENU;
                case BACK_TO_GIVE_REGION, WRITE_ADDRESS -> state = BotState.GIVE_REGION;
                case BACK_TO_GIVE_DISTRICT -> state = BotState.GIVE_DISTRICT;
                case BACK_TO_GIVE_ADDRESS -> state = BotState.GIVE_ADDRESS;
                case BACK_TO_GIVE_HOME_TYPE -> state = BotState.GIVE_HOME_TYPE_EDIT;
                case BACK_TO_GIVE_HOME_NUMBER -> state = BotState.GIVE_HOME_NUMBER;
                case BACK_TO_GIVE_HOME_AREA -> state = BotState.GIVE_HOME_AREA_EDIT;
                case BACK_TO_GIVE_HOME_PHOTO -> state = BotState.GIVE_HOME_PHOTO_EDIT;
                case BACK_TO_GIVE_HOME_PRICE -> state = BotState.GIVE_HOME_PRICE_EDIT;
                case BACK_TO_SHOW_MENU_EDIT, SHOW_ACCOMMODATIONS -> state = BotState.SHOW_MENU_EDIT;
                case BACK_TO_SHOW_MENU_SEND -> state = BotState.SHOW_MENU_SEND;
                case SHOW_ALL -> state = BotState.SHOW_OPTIONS;
                case BACK_TO_CHOOSE_REGION, SEARCH -> state = BotState.CHOOSE_REGION;
                case BACK_TO_CHOOSE_DISTRICT -> state = BotState.CHOOSE_DISTRICT;
                case BACK_TO_CHOOSE_HOME_STATUS -> state = BotState.CHOOSE_HOME_STATUS;
                case BACK_TO_CHOOSE_HOME_TYPE, GET_RENTING, FOR_BUY -> state = BotState.CHOOSE_HOME_TYPE;
                case BACK_TO_CHOOSE_HOME_NUMBER -> state = BotState.CHOOSE_HOME_NUMBER_EDIT;
                case BACK_TO_CHOOSE_MIN_PRICE -> state = BotState.CHOOSE_HOME_MIN_PRICE_EDIT;
                case BACK_TO_CHOOSE_MAX_PRICE_SEND -> state = BotState.CHOOSE_HOME_MAX_PRICE_SEND;
                case BACK_TO_CHOOSE_MAX_PRICE_EDIT -> state = BotState.CHOOSE_HOME_MAX_PRICE_EDIT;
                case BACK_TO_MY_NOTES_MENU_EDIT, MY_NOTES -> state = BotState.MY_NOTES_MENU_EDIT;
                case BACK_TO_MY_NOTES_MENU_SEND -> state = BotState.MY_NOTES_MENU_SEND;
                case MY_FAVOURITES -> state = BotState.MY_FAVOURITES;
                case MY_ACCOMMODATIONS -> state = BotState.MY_HOMES;
                case SKIP -> state = BotService.getStateBySkip(update, state);
                default -> state = BotService.getState(update, state);
            }
        }
        switch (state) {
            case CHOOSE_LANGUAGE -> execute(BotService.chooseLanguage(update, lan));
            case ADMIN_MENU_SEND -> {
                execute(BotService.getAdminMenuSend(update, lan));
                BotService.changeIsAdminUser(update,false);
            }
            case ADMIN_MENU_EDIT -> {
                execute(BotService.getAdminMenuEdit(update, lan));
                BotService.changeIsAdminUser(update,false);
            }
            case ADMIN_PANEL -> {
                if (role.equals(Role.ADMIN)){
                    new AdminPanel().onUpdateReceived(update);
                    BotService.changeIsAdminUser(update,true);
                }
            }
            case CHANGE_LANGUAGE -> execute(BotService.changeLanguage(update, lan));
            case MAIN_MENU_EDIT -> execute(BotService.setMenuEdit(update, lan,role));
            case MAIN_MENU_SEND -> execute(BotService.setMenuSend(update, lan,role));
            case SETTINGS_MENU_EDIT -> execute(BotService.getSettingMenuEdit(update, lan));
            case SETTINGS_MENU_SEND -> {
                if (BotService.saveContact(update, lan)) {
                    execute(BotService.removeKeyBoardMarkup(update));
                    execute(BotService.getSettingMenuSend(update, lan));
                } else {
                    state = BotState.REGISTER;
                    execute(BotService.getRegister(update, lan));
                }
            }
            case REGISTER -> {
                execute(BotService.deleteMessage(update));
                execute(BotService.getRegister(update, lan));
            }
            case GIVE_HOME_STATUS -> {
                if (BotService.checkByPhone(update))
                    execute(BotService.setHomeStatusMenu(update, lan));
                else {
                    execute(BotService.setWarningRegister(update, lan));
                    execute(BotService.setMenuSend(update, lan,role));
                }
            }
            case WRITE_OR_SEND_LOCATION -> {
                if (update.hasCallbackQuery())
                    execute(BotService.setWriteOrSendLocationMenuEdit(update, lan));
                else if (update.hasMessage()) {
                    if (update.getMessage().getText().equals(LanguageService.getWord(BACK, lan))) {
                        execute(BotService.removeKeyBoardMarkup(update));
                        execute(BotService.setWriteOrSendLocationMenuSend(update, lan));
                    } else {
                        execute(BotService.getMenuLocation(update, lan));
                        state = BotState.LOCATION_MENU;
                    }

                }
            }
            case LOCATION_MENU -> {
                execute(BotService.deleteMessage(update));
                execute(BotService.getMenuLocation(update, lan));
            }
            case GIVE_REGION -> execute(BotService.giveRegionMenu(update, lan));
            case GIVE_DISTRICT -> execute(BotService.giveDistrictMenu(update, lan, state));
            case GIVE_ADDRESS -> execute(BotService.giveAddressMenu(update, lan, state));
            case GIVE_HOME_TYPE_SEND -> {
                if (BotService.checkLocation(update)) {
                    execute(BotService.removeKeyBoardMarkup(update));
                    execute(BotService.giveHomeTypeMenuSend(update, lan, state));
                } else {
                    execute(BotService.LocationNotFound(update, lan));
                    execute(BotService.getMenuLocation(update, lan));
                    state = BotState.LOCATION_MENU;
                }
            }
            case GIVE_HOME_TYPE_EDIT -> execute(BotService.giveHomeTypeMenuEdit(update, lan));
            case GIVE_HOME_NUMBER -> execute(BotService.giveHomeNumberMenu(update, lan, state));
            case GIVE_HOME_AREA_SEND -> {
                if (BotService.saveNumberOfRoom(update, state))
                    execute(BotService.giveHomeAreaMenuSend(update, lan));
                else {
                    execute(BotService.giveHomeNumberMenuSend(update, lan, state));
                    state = BotState.GIVE_HOME_NUMBER;
                }
            }
            case GIVE_HOME_AREA_EDIT -> execute(BotService.giveHomeAreaMenuEdit(update, lan));
            case GIVE_HOME_PHOTO_SEND -> {
                if (BotService.saveHomeArea(update, state))
                    execute(BotService.giveHomePhotoMenuSend(update, lan));
                else {
                    execute(BotService.giveHomeAreaMenuSend(update, lan));
                    state = BotState.GIVE_HOME_AREA_SEND;
                }
            }
            case GIVE_HOME_PHOTO_EDIT -> execute(BotService.giveHomePhotoMenuEdit(update, lan));
            case GIVE_HOME_PRICE_SEND -> execute(BotService.giveHomePriceMenuSend(update, lan, state));
            case GIVE_HOME_PRICE_EDIT -> execute(BotService.giveHomePriceMenuEdit(update, lan));
            case GIVE_HOME_DESCRIPTION -> {
                if (BotService.saveHomePrice(update, state))
                    execute(BotService.giveHomeDescription(update, lan));
                else {
                    execute(BotService.giveHomePriceMenuSend(update, lan, state));
                    state = BotState.GIVE_HOME_PRICE_SEND;
                }
            }
            case SAVE_HOME_TO_STORE -> {
                BotService.saveHomeDescription(update, state);
                execute(BotService.successfullySaved(update, lan));
                execute(BotService.setMenuSend(update, lan,role));
            }
            case SHOW_MENU_SEND -> execute(BotService.getShowMenuSend(update, lan));
            case SHOW_MENU_EDIT -> execute(BotService.getShowMenuEdit(update, lan));
            case SHOW_OPTIONS -> {
                execute(BotService.deleteMessage(update));
                List<SendPhoto> sendPhotos = BotService.showAllHomes(update, lan);
                if (sendPhotos == null) {
                    execute(BotService.homeNotFound(update, lan, BACK_TO_SHOW_MENU_EDIT));
                    return;
                }
                for (SendPhoto sendPhoto : sendPhotos)
                    execute(sendPhoto);
            }
            case SHOW_HOME_PHONE_MENU_ALL -> {
                execute(BotService.changeVisibleHomePhone(update, lan, Constant.BACK_TO_SHOW_MENU_SEND, state));
                state = BotState.SHOW_OPTIONS;
            }
            case SHOW_HOME_PHONE_MENU_FAVOURITES -> {
                execute(BotService.changeVisibleHomePhone(update, lan, Constant.BACK_TO_MY_NOTES_MENU_SEND, state));
                state = BotState.MY_FAVOURITES;
            }
            case SHOW_HOME_PHONE_MENU_MY_ACCOMMODATIONS -> {
                execute(BotService.changeVisibleHomePhone(update, lan, Constant.BACK_TO_MY_NOTES_MENU_SEND, state));
                state = BotState.MY_HOMES;
            }
            case SHOW_HOME_PHONE_MENU_SEARCH -> {
                execute(BotService.changeVisibleHomePhone(update, lan, Constant.BACK_TO_CHOOSE_MAX_PRICE_SEND, state));
                state = BotState.SHOW_SORTED_OPTIONS;
            }
            case CHANGE_HOME_LIKE_MENU_ALL -> {
                execute(BotService.changeHomeLike(update, lan, Constant.BACK_TO_SHOW_MENU_SEND, state));
                state = BotState.SHOW_OPTIONS;
            }
            case CHANGE_HOME_LIKE_MENU_FAVOURITES -> {
                execute(BotService.changeHomeLike(update, lan, Constant.BACK_TO_MY_NOTES_MENU_SEND, state));
                state = BotState.MY_FAVOURITES;
            }
            case CHANGE_HOME_LIKE_MENU_MY_ACCOMMODATIONS -> {
                execute(BotService.changeHomeLike(update, lan, Constant.BACK_TO_MY_NOTES_MENU_SEND, state));
                state = BotState.MY_HOMES;
            }
            case CHANGE_HOME_LIKE_MENU_SEARCH -> {
                execute(BotService.changeHomeLike(update, lan, Constant.BACK_TO_CHOOSE_MAX_PRICE_SEND, state));
                state = BotState.SHOW_SORTED_OPTIONS;
            }
            case CHOOSE_REGION -> execute(BotService.chooseRegionMenu(update, lan));
            case CHOOSE_DISTRICT -> execute(BotService.chooseDistrict(update, lan));
            case CHOOSE_HOME_STATUS -> execute(BotService.chooseHomeStatus(update, lan));
            case CHOOSE_HOME_TYPE -> execute(BotService.chooseHomeType(update, lan));
            case CHOOSE_HOME_NUMBER_EDIT -> execute(BotService.chooseHomeNumberEdit(update, lan));
            case CHOOSE_HOME_MIN_PRICE_SEND -> {
                if (BotService.saveSearchNumber(update)) {
                    execute(BotService.chooseMinPriceMenuSend(update, lan));
                } else {
                    execute(BotService.chooseHomeNumberSend(update, lan));
                    state = BotState.CHOOSE_HOME_NUMBER_SEND;
                }
            }
            case CHOOSE_HOME_MIN_PRICE_EDIT -> execute(BotService.chooseMinPriceMenuEdit(update, lan));
            case CHOOSE_HOME_MAX_PRICE_SEND -> {
                if (update.hasMessage() && update.getMessage().hasText()) {
                    if (BotService.saveSearchMinPrice(update)) {
                        execute(BotService.chooseMaxPriceMenuSend(update, lan));
                    } else {
                        execute(BotService.chooseMinPriceMenuSend(update, lan));
                        state = BotState.CHOOSE_HOME_MIN_PRICE_SEND;
                    }
                } else
                    execute(BotService.chooseMaxPriceMenuSend(update, lan));
            }
            case CHOOSE_HOME_MAX_PRICE_EDIT -> execute(BotService.chooseMaxPriceMenuEdit(update, lan));
            case SHOW_SORTED_OPTIONS -> {
                if (BotService.saveSearchMaxPrice(update)) {
                    execute(BotService.deleteMessage(update));
                    List<SendPhoto> sendPhotos = BotService.showSortedOptionsSend(update, lan);
                    if (sendPhotos == null) {
                        execute(BotService.homeNotFound(update, lan, BACK_TO_CHOOSE_MAX_PRICE_EDIT));
                        return;
                    }
                    for (SendPhoto sendPhoto : sendPhotos)
                        execute(sendPhoto);
                } else {
                    execute(BotService.chooseMaxPriceMenuSend(update, lan));
                    state = BotState.CHOOSE_HOME_MAX_PRICE_SEND;
                }
            }
            case MY_NOTES_MENU_EDIT -> execute(BotService.getMyNotesMenuEdit(update, lan));
            case MY_NOTES_MENU_SEND -> execute(BotService.getMyNotesMenuSend(update, lan));
            case MY_FAVOURITES -> {
                execute(BotService.deleteMessage(update));
                List<SendPhoto> sendPhotos = BotService.getMyFavouriteHomes(update, lan);
                if (sendPhotos == null) {
                    execute(BotService.homeNotFound(update, lan, BACK_TO_MY_NOTES_MENU_EDIT));
                    return;
                }
                for (SendPhoto sendPhoto : sendPhotos)
                    execute(sendPhoto);
            }
            case MY_HOMES -> {
                execute(BotService.deleteMessage(update));
                List<SendPhoto> sendPhotos = BotService.getMyHomes(update, lan);
                if (sendPhotos == null) {
                    execute(BotService.homeNotFound(update, lan, BACK_TO_MY_NOTES_MENU_EDIT));
                    return;
                }
                for (SendPhoto sendPhoto : sendPhotos)
                    execute(sendPhoto);
            }
            case DELETE_ACCOMMODATION -> execute(BotService.deleteAccommodation(update));
            default -> execute(BotService.setError(update));
        }
        UserService.saveStateAndLan(update, new LanStateDTO(lan, state, role,isAdmin));
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
