package service.adminService;

import model.Home;
import model.Like;
import model.User;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.*;
import util.constant.Constant;
import util.enums.HomeStatus;
import util.enums.HomeType;
import util.enums.Language;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminBotService extends LanguageService implements Constant {
    public static EditMessageText setAdminMenuEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADMIN_SHOW_USERS, ADMIN_SHOW_HOMES),
                List.of(BACK_TO_ADMIN_MENU)
        ), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(ADMIN_USERS_SHOW_MENU_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage setAdminMenuSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADMIN_SHOW_USERS, ADMIN_SHOW_HOMES)), lan);
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(ADMIN_USERS_SHOW_MENU_TEXT, lan));
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText setAdminShowUsersEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADMIN_EXCEL_FILE, SEARCH),
                List.of(BACK_TO_ADMIN_MAIN_MENU)), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(ADMIN_USERS_SHOW_MENU_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage setAdminShowUsersSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADMIN_EXCEL_FILE, SEARCH),
                List.of(BACK_TO_ADMIN_MAIN_MENU)), lan);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText setAdminChooseUsersEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADMIN_ACTIVE_USERS, ADMIN_DEACTIVATED_USERS),
                List.of(BACK_TO_ADMIN_USERS_SHOW)), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(ADMIN_USERS_SHOW_MENU_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage setAdminChooseUsersSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADMIN_ACTIVE_USERS, ADMIN_DEACTIVATED_USERS),
                List.of(BACK_TO_ADMIN_USERS_SHOW)), lan);
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(ADMIN_CHOOSE_USERS_MENU_TEXT, lan));
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText setAdminUserEnterPhoneEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(ADMIN_ENTER_PHONE_NUMBER_MENU_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton back = new InlineKeyboardButton(getWord(BACK, lan));
        back.setCallbackData(BACK_TO_ADMIN_USERS_SHOW);
        inlineKeyboardMarkup.setKeyboard(List.of(List.of(back)));
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    public static SendMessage setAdminUserEnterPhoneNumberSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(ADMIN_ENTER_PHONE_NUMBER_MENU_TEXT, lan));
        sendMessage.setChatId(message.getChatId().toString());
        return sendMessage;
    }

    public static EditMessageText setAdminHomeFilterEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADMIN_HOMES_IN_WEEK, ADMIN_HOMES_IN_DAY),
                List.of(BACK_TO_ADMIN_MAIN_MENU)), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(ADMIN_HOMES_FILTER_MENU_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage setAdminHomeFilterSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADMIN_HOMES_IN_WEEK, ADMIN_HOMES_IN_DAY),
                List.of(BACK_TO_ADMIN_MAIN_MENU)), lan);
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(ADMIN_HOMES_FILTER_MENU_TEXT, lan));
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    private static String getCaptionByHome(Home home, Language lan) {
        return getWord(HOUSE_TYPE, lan) + "\t" + getWord(home.getHomeType().equals(HomeType.HOUSE) ? HOUSE : FLAT, lan) + "\t\t\t\t\t\t\t\t\t\t" +
                getWord(ADMIN_HOMES_INFO_NUMBER_OF_INTERESTED, lan) + home.getInterests() + "\n" +
                getWord(STATUS, lan) + "\t" + getWord(home.getStatus().equals(HomeStatus.SELL) ? SELL : RENT, lan) + "\n" +
                getWord(ADMIN_HOMES_INFO_REGION, lan) + home.getRegion() + "\n" +
                getWord(ADMIN_HOMES_INFO_DISTRICT, lan) + (home.getDistrict() != null ? home.getDistrict() : " ") + "\n" +
                getWord(ADDRESS, lan) + home.getAddress() + "\n" +
                getWord(NUMBER_OF_ROOMS, lan) + home.getNumberOfRooms() + "\n" +
                getWord(AREA, lan) + home.getArea() + " m²" + "\n" +
                getWord(DATE, lan) + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDateTime.parse(home.getCreatedDate())) + "\n" +
                getWord(ADMIN_HOMES_INFO_PRICE, lan) + home.getPrice() + " $" + "\n" +
                getWord(ADMIN_USER_INFO_PHONE_NUMBER, lan) + HomeService.getHomeOwnerPhoneNumber(home.getOwnerId()) + "\n" +
                getWord(DESCRIPTION, lan) + home.getDescription();
    }


    public static List<SendPhoto> showHomes(Update update, Language lan, String searchType) {
        InlineKeyboardMarkup markup = null;
        List<List<InlineKeyboardButton>> rowList;
        List<InlineKeyboardButton> row1;
        List<InlineKeyboardButton> row2 = null;
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        List<Home> allHome;
        if (searchType.equals(ADMIN_HOMES_IN_WEEK)) {
            allHome = HomeService.getWeekHomes();
        } else
            allHome = HomeService.getDayHomes();

        if (allHome.isEmpty())
            return null;

        List<SendPhoto> sendMessageList = new ArrayList<>();
        SendPhoto sendPhoto = null;

        for (Home home : allHome) {
            if (sendPhoto != null)
                sendMessageList.add(sendPhoto);
            markup = new InlineKeyboardMarkup();
            rowList = new ArrayList<>();
            markup.setKeyboard(rowList);
            row1 = new ArrayList<>();
            row2 = new ArrayList<>();
            rowList.add(row1);
            rowList.add(row2);

            sendPhoto = new SendPhoto(message.getChatId().toString(), new InputFile(home.getFileId()));
            sendPhoto.setCaption(getCaptionByHome(home, lan));

            InlineKeyboardButton banButton = new InlineKeyboardButton(DELETE);
            banButton.setCallbackData(home.getId().toString());
            row1.add(banButton);
            sendPhoto.setReplyMarkup(markup);
        }

        InlineKeyboardButton back = new InlineKeyboardButton(getWord(BACK, lan));
        back.setCallbackData(BACK_TO_ADMIN_HOMES_FILTER);
        row2.add(0, back);
        sendPhoto.setReplyMarkup(markup);
        sendMessageList.add(sendPhoto);
        return sendMessageList;
    }

    public static SendDocument getExelFile(Update update, Language lan, boolean isActive) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InputFile activeOrBannedUsers = UserService.getActiveOrBannedUsers(isActive);
        SendDocument sendDocument = new SendDocument(message.getChatId().toString(), activeOrBannedUsers);
        sendDocument.setCaption(getWord(isActive ? ADMIN_ACTIVE_USERS : ADMIN_DEACTIVATED_USERS, lan));
        InlineKeyboardButton button = new InlineKeyboardButton(getWord(BACK, lan));
        button.setCallbackData(BACK_TO_ADMIN_CHOOSE_USER_TYPE);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(List.of(
                List.of(button)
        ));
        sendDocument.setReplyMarkup(inlineKeyboardMarkup);
        return sendDocument;
    }

    public static SendMessage sendAdminUserInfo(Update update, Language lan) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        User user = UserService.getSearchUser(message.getText());
        sendMessage.setText(getCaptionByUser(user, lan));

        InlineKeyboardButton buttonBan = new InlineKeyboardButton(!user.isActive() ? BAN : REMOVE_BAN);
        buttonBan.setCallbackData(user.getPhoneNumber());
        InlineKeyboardButton buttonBack = new InlineKeyboardButton(getWord(BACK, lan));
        buttonBack.setCallbackData(BACK_TO_ADMIN_USERS_SHOW);
        List<List<InlineKeyboardButton>> lines = List.of(List.of(buttonBan), List.of(buttonBack));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(lines);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public static String getCaptionByUser(User user, Language lan) {
        return getWord(ADMIN_USER_INFO_NAME, lan) + " :    " + user.getName() + "\n" +
                getWord(ADMIN_USER_INFO_USERNAME, lan) + " :    " + user.getUsername() + "\n" +
                getWord(ADMIN_USER_INFO_PHONE_NUMBER, lan) + " :    " + user.getPhoneNumber() + "\n" +
                getWord(ADMIN_USER_INFO_LANGUAGE, lan) + " :    " + user.getLanguage() + "\n" +
                getWord(ROLE, lan) + " :    " + user.getRole() + "\n" +
                getWord(SITUATION,lan)+"     " + (user.isActive() ? getWord(ACTIVE,lan) : getWord(DE_ACTIVE,lan));
    }

    public static EditMessageText editAdminUserInfo(Update update, Language lan) {

        Message message = update.getCallbackQuery().getMessage();
        String text = update.getCallbackQuery().getData();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setMessageId(message.getMessageId());
        editMessage.setChatId(message.getChatId().toString());
        User user = UserService.banOrUnbanUser(text);
        editMessage.setText(getCaptionByUser(user, lan));

        InlineKeyboardButton buttonBan = new InlineKeyboardButton(!user.isActive() ? BAN : REMOVE_BAN);
        buttonBan.setCallbackData(user.getPhoneNumber());
        InlineKeyboardButton buttonBack = new InlineKeyboardButton(getWord(BACK, lan));
        buttonBack.setCallbackData(BACK_TO_ADMIN_USERS_SHOW);
        List<List<InlineKeyboardButton>> lines = List.of(List.of(buttonBan), List.of(buttonBack));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(lines);

        editMessage.setReplyMarkup(inlineKeyboardMarkup);
        return editMessage;
    }

    public static DeleteMessage sendAdminDeleteHome(Update update, Language lan) {
        Message message = update.getCallbackQuery().getMessage();
        DeleteMessage deleteMessage = new DeleteMessage();
        String homeId = update.getCallbackQuery().getData();
        HomeService.deleteHome(UUID.fromString(homeId));
        deleteMessage.setMessageId(message.getMessageId());
        deleteMessage.setChatId(message.getChatId().toString());
        return deleteMessage;
    }


}
