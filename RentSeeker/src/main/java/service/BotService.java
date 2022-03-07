package service;

import model.Home;
import model.Like;
import model.Search;
import model.User;
import model.locationModels.LocationsItem;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import util.constant.Constant;
import util.enums.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BotService extends LanguageService {
    public static SendMessage chooseLanguage(Update update, Language lan) {
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(UZ, RU, EN)
        ), lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(CHOOSE_LANGUAGE_MENU_TEXT, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText changeLanguage(Update update, Language lan) {
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(UZ, RU, EN),
                List.of(BACK_TO_SETTINGS_MENU_EDIT)
        ), lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(CHOOSE_LANGUAGE_MENU_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }


    public static EditMessageText getMainMenuEditForAdminEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADD_ACCOMMODATION,
                        SHOW_ACCOMMODATIONS),
                List.of(MY_NOTES,
                        SETTINGS),
                List.of(BACK_TO_ADMIN_MENU)
        ), lan);

        EditMessageText editMessageText = new EditMessageText(getWord(Constant.MENU_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }
    public static SendMessage getMainMenuEditForAdminSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADD_ACCOMMODATION,
                        SHOW_ACCOMMODATIONS),
                List.of(MY_NOTES,
                        SETTINGS),
                List.of(BACK_TO_ADMIN_MENU)
        ), lan);

        SendMessage sendMessage = new SendMessage(message.getChatId().toString(),getWord(Constant.MENU_TEXT, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }


    public static EditMessageText getAdminMenuEdit(Update update, Language lan) {
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(USER, ADMIN)), lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessage = new EditMessageText(getWord(CHOOSE_ACTION, lan));
        editMessage.setChatId(message.getChatId().toString());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setReplyMarkup(markup);
        return editMessage;
    }


    public static SendMessage getAdminMenuSend(Update update, Language lan) {
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(USER, ADMIN)), lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(CHOOSE_ACTION, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText setMenuEdit(Update update, Language lan,Role role) {
        if(role.equals(Role.ADMIN)) return getMainMenuEditForAdminEdit(update,lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADD_ACCOMMODATION,
                        SHOW_ACCOMMODATIONS),
                List.of(MY_NOTES,
                        SETTINGS)), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(Constant.MENU_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage setMenuSend(Update update, Language lan,Role role) {
        if(role.equals(Role.ADMIN)) return getMainMenuEditForAdminSend(update,lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(ADD_ACCOMMODATION,
                        SHOW_ACCOMMODATIONS),
                List.of(MY_NOTES,
                        SETTINGS)), lan);
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(Constant.MENU_TEXT, lan));
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText getSettingMenuEdit(Update update, Language lan) {
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(CHANGE_LANGUAGE, REGISTRATION),
                List.of(BACK_TO_MAIN_MENU_EDIT)
        ), lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(CHOOSE_ACTION, lan));
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    public static SendMessage getSettingMenuSend(Update update, Language lan) {
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(CHANGE_LANGUAGE, REGISTRATION),
                List.of(BACK_TO_MAIN_MENU_EDIT)
        ), lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(CHOOSE_ACTION, lan));
        sendMessage.setReplyMarkup(markup);
        sendMessage.setChatId(message.getChatId().toString());
        return sendMessage;
    }

    public static boolean saveContact(Update update, Language lan) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && !message.getText().equals(getWord(BACK, lan))) {
                String phone = message.getText();
                if (UserService.phoneNumberValidation(phone)) {
                    UserService.savePhoneNumber(phone, message.getChatId().toString());
                } else return false;
            } else if (message.hasContact()) {
                Contact contact = message.getContact();
                UserService.savePhoneNumber(contact.getPhoneNumber(), message.getChatId().toString());
            }
        }
        return true;
    }

    public static SendMessage getRegister(Update update, Language lan) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);
        List<KeyboardRow> rowList = new ArrayList<>();
        markup.setKeyboard(rowList);
        KeyboardRow row = new KeyboardRow();
        rowList.add(row);
        KeyboardButton button = new KeyboardButton(getWord(MY_PHONE_NUMBER, lan));
        button.setRequestContact(true);
        row.add(button);
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(getWord(BACK, lan)));
        rowList.add(row1);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        User user = UserService.getByChatId(message.getChatId().toString());
        assert user != null;
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), user.getName() + getWord(ENTER_PHONE_NUMBER_TEXT, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }


    public static SendMessage setError(Update update) {
        Message message = update.hasMessage() ? update.getMessage() :
                update.getCallbackQuery().getMessage();
        return new SendMessage(message.getChatId().toString(), Constant.ERROR);
    }

    public static DeleteMessage deleteMessage(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        return new DeleteMessage(message.getChatId().toString(), message.getMessageId());
    }

    public static SendMessage removeKeyBoardMarkup(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), "⬅️⬅️⬅️");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;
    }


    public static EditMessageText setHomeStatusMenu(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(FOR_RENTING, FOR_SELLING),
                List.of(BACK_TO_MAIN_MENU_EDIT)
        ), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(SET_ACCOMMODATION_FOR_MENU_TEXT, lan));
        editMessageText.setReplyMarkup(markup);
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        return editMessageText;
    }

    public static EditMessageText setWarningRegister(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(REGISTER_WARNING_TEXT, lan));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    public static EditMessageText setWriteOrSendLocationMenuEdit(Update update, Language lan) {
        saveHomeStatus(update);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(WRITE_ADDRESS, SEND_LOCATION),
                List.of(BACK_TO_GIVE_HOME_STATUS)
        ), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(WRITE_SEND_LOCATION_TEXT, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage setWriteOrSendLocationMenuSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(WRITE_ADDRESS, SEND_LOCATION),
                List.of(BACK_TO_GIVE_HOME_STATUS)
        ), lan);
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(WRITE_SEND_LOCATION_TEXT, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static SendMessage getMenuLocation(Update update, Language lan) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);
        List<KeyboardRow> rowList = new ArrayList<>();
        markup.setKeyboard(rowList);
        KeyboardRow row = new KeyboardRow();
        rowList.add(row);
        KeyboardButton button = new KeyboardButton(getWord(SEND_LOCATION, lan));
        button.setRequestLocation(true);
        row.add(button);
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(getWord(BACK, lan)));
        rowList.add(row1);

        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        User user = UserService.getByChatId(message.getChatId().toString());
        assert user != null;
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(SEND_LOCATION_TEXT, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText giveRegionMenu(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (Region value : Region.values()) {
            if (row.size() != 3) {
                row.add(value.name());
            } else {
                rows.add(row);
                row = new ArrayList<>();
                row.add(value.name());
            }
        }
        rows.add(row);
        rows.add(List.of(BACK_TO_WRITE_SEND_LOCATION));
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(rows, lan);
        EditMessageText editMessageText = new EditMessageText(getWord(CHOOSE_REGION_MENU_TEXT, lan));
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    public static EditMessageText giveDistrictMenu(Update update, Language lan, BotState state) {
        Message message = update.getCallbackQuery().getMessage();
        Region region;
        if (!update.getCallbackQuery().getData().contains("BACK"))
            region = saveHomeRegion(update, state);
        else
            region = HomeService.getNoActiveHomeByChatId(message.getChatId().toString()).getRegion();


        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (District value : District.values())
            if (value.getRegionId() == region.getId()) {
                if (row.size() != 3) {
                    row.add(value.name());
                } else {
                    rows.add(row);
                    row = new ArrayList<>();
                    row.add(value.name());
                }
            }
        rows.add(row);
        rows.add(List.of(BACK_TO_GIVE_REGION));
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(rows, lan);
        EditMessageText editMessageText = new EditMessageText(getWord(CHOOSE_DISTRICT_MENU_TEXT, lan));
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    // SAVE HOME DATA
    public static void saveHomeStatus(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        User user = UserService.getByChatId(query.getMessage().getChatId().toString());
        Home home = new Home();
        home.setStatus(query.getData().equals(FOR_RENTING) ? HomeStatus.RENT : query.getData().equals(FOR_SELLING) ? HomeStatus.SELL : null);
        home.setOwnerId(user.getId());
        HomeService.saveHome(home);
    }

    private static Region saveHomeRegion(Update update, BotState state) {
        CallbackQuery query = update.getCallbackQuery();
        String chatId = query.getMessage().getChatId().toString();
        User user = UserService.getByChatId(chatId);
        Region region = Region.valueOf(query.getData());
        Home home = new Home();
        home.setOwnerId(user.getId());
        home.setRegion(region);
        HomeService.addHome(home, chatId, state);
        return region;
    }

    private static void saveHomeDistrict(Update update, BotState state) {
        CallbackQuery query = update.getCallbackQuery();
        String chatId = query.getMessage().getChatId().toString();
        User user = UserService.getByChatId(chatId);
        Home home = new Home();
        home.setOwnerId(user.getId());
        home.setDistrict(District.valueOf(query.getData()));
        HomeService.addHome(home, chatId, state);
    }

    private static void saveHomeAddress(Update update, BotState state) {
        Message message = update.getMessage();
        User user = UserService.getByChatId(message.getChatId().toString());
        Home home = new Home();
        home.setOwnerId(user.getId());
        home.setAddress(message.getText());
        HomeService.addHome(home, user.getChatId(), state);
    }

    private static void saveHomeType(Update update, BotState state) {
        CallbackQuery query = update.getCallbackQuery();
        User user = UserService.getByChatId(query.getMessage().getChatId().toString());
        Home home = new Home();
        home.setOwnerId(user.getId());
        home.setHomeType(HomeType.valueOf(query.getData().toUpperCase()));
        HomeService.addHome(home, user.getChatId(), state);
    }

    public static boolean saveNumberOfRoom(Update update, BotState state) {
        Message message = update.getMessage();
        String text = message.getText();
        for (char c : text.toCharArray())
            if (!Character.isDigit(c))
                return false;
        int rooms = Integer.parseInt(text);
        if (rooms > 15)
            return false;

        User user = UserService.getByChatId(message.getChatId().toString());
        Home home = new Home();
        home.setOwnerId(user.getId());
        home.setNumberOfRooms(rooms);
        HomeService.addHome(home, user.getChatId(), state);
        return true;
    }

    public static boolean saveHomeArea(Update update, BotState state) {
        Message message = update.getMessage();
        String text = message.getText();
        User user = UserService.getByChatId(message.getChatId().toString());
        Home home = new Home();
        home.setOwnerId(user.getId());

        try {
            home.setArea(Double.parseDouble(text));
        } catch (Exception e) {
            return false;
        }

        HomeService.addHome(home, user.getChatId(), state);
        return true;
    }

    public static void saveHomePhoto(Update update, BotState state) {
        Message message = update.getMessage();
        PhotoSize photoSize = message.getPhoto().get(message.getPhoto().size() - 1);
        Home home = new Home();
        User user = UserService.getByChatId(message.getChatId().toString());
        home.setFileId(photoSize.getFileId());
        home.setFileSize(photoSize.getFileSize());
        home.setOwnerId(user.getId());
        HomeService.addHome(home, message.getChatId().toString(), state);
    }

    public static boolean saveHomePrice(Update update, BotState state) {
        Message message = update.getMessage();
        String text = message.getText();
        User user = UserService.getByChatId(message.getChatId().toString());
        Home home = new Home();
        home.setOwnerId(user.getId());
        try {
            double price = Double.parseDouble(text);
            if (price <= 0) return false;
            home.setPrice(price);
        } catch (Exception e) {
            return false;
        }

        HomeService.addHome(home, user.getChatId(), state);
        return true;
    }

    public static void saveHomeDescription(Update update, BotState state) {
        Message message = update.getMessage();
        String text = message.getText();
        User user = UserService.getByChatId(message.getChatId().toString());
        Home home = new Home();
        home.setOwnerId(user.getId());
        home.setDescription(text);
        HomeService.addHome(home, user.getChatId(), state);
    }
    //

    // SAVE SEARCH DATA
    private static Region saveSearchRegion(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        String chatId = query.getMessage().getChatId().toString();
        User user = UserService.getByChatId(chatId);
        Search search = new Search();
        search.setUserId(user.getId());

        if (query.getData().equals(SKIP) || query.getData().contains(BACK)) {
            search.setRegion(null);
            SearchService.addSearch(search);
            return null;
        } else {
            try {
                Region region = Region.valueOf(query.getData());
                search.setRegion(region);
                SearchService.addSearch(search);
                return region;
            } catch (Exception e) {
                search.setRegion(null);
                SearchService.addSearch(search);
                return null;
            }
        }

    }

    private static void saveSearchDistrict(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        String chatId = query.getMessage().getChatId().toString();
        User user = UserService.getByChatId(chatId);
        Search search = SearchService.getSearch(user.getId());
        if (search == null) {
            search = new Search();
            search.setUserId(user.getId());
        }
        search.setDistrict(query.getData().equals(SKIP) || query.getData().contains("BACK") ? null : District.valueOf(query.getData()));
        SearchService.addSearch(search);
    }

    public static void saveSearchStatus(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        User user = UserService.getByChatId(query.getMessage().getChatId().toString());
        Search search = SearchService.getSearch(user.getId());
        if (search == null) {
            search = new Search();
            search.setUserId(user.getId());
        }
        search.setStatus(query.getData().equals(GET_RENTING) ? HomeStatus.RENT : query.getData().equals(FOR_BUY) ? HomeStatus.SELL : null);
        SearchService.addSearch(search);
    }

    private static void saveSearchType(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        User user = UserService.getByChatId(query.getMessage().getChatId().toString());
        Search search = SearchService.getSearch(user.getId());
        if (search == null) {
            search = new Search();
            search.setUserId(user.getId());
        }
        search.setHomeType(query.getData().equals(SKIP) || query.getData().contains("BACK") ? null : HomeType.valueOf(query.getData().toUpperCase()));
        SearchService.addSearch(search);
    }

    public static boolean saveSearchNumber(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        UUID userId = UserService.getByChatId(message.getChatId().toString()).getId();
        Search search = SearchService.getSearch(userId);
        if (search == null) {
            search = new Search();
            search.setUserId(userId);
        }
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            if (data.contains("BACK"))
                search.setNumberOfRooms(-1);
            SearchService.addSearch(search);
            return true;
        }
        String text = message.getText();
        for (char c : text.toCharArray())
            if (!Character.isDigit(c))
                return false;
        int rooms = Integer.parseInt(text);
        if (rooms > 15)
            return false;
        search.setNumberOfRooms(rooms);
        SearchService.addSearch(search);
        return true;
    }

    public static boolean saveSearchMinPrice(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        UUID userId = UserService.getByChatId(message.getChatId().toString()).getId();
        Search search = SearchService.getSearch(userId);
        if (search == null) {
            search = new Search();
            search.setUserId(userId);
        }
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();

            if (data.contains("BACK"))
                search.setMinPrice(-1);

            SearchService.addSearch(search);
            return true;
        }
        String text = message.getText();
        double price;
        try {
            price = Double.parseDouble(text);
        } catch (Exception e) {
            return false;
        }
        search.setMinPrice(price);
        SearchService.addSearch(search);
        return true;
    }

    public static boolean saveSearchMaxPrice(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        UUID userId = UserService.getByChatId(message.getChatId().toString()).getId();
        Search search = SearchService.getSearch(userId);
        if (search == null) {
            search = new Search();
            search.setUserId(userId);
        }
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            if (data.contains("BACK"))
                search.setMaxPrice(-1);
            SearchService.addSearch(search);
            return true;

        }
        String text = message.getText();
        double price;
        try {
            price = Double.parseDouble(text);
        } catch (Exception e) {
            return false;
        }
        search.setMaxPrice(price);
        SearchService.addSearch(search);
        return true;
    }
    //

    // CHECKING SOMETHING

    public static boolean isRegion(Update update) {
        String data = update.getCallbackQuery().getData();
        for (Region value : Region.values())
            if (data.equals(value.name()))
                return true;
        return false;
    }

    public static boolean isHomeType(Update update) {
        String data = update.getCallbackQuery().getData();
        return data.equals(FLAT) || data.equals(HOUSE);
    }

    public static boolean checkByPhone(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        User user = UserService.getByChatId(message.getChatId().toString());
        assert user != null;
        return !user.getPhoneNumber().equals("");
    }
    //


    public static EditMessageText giveAddressMenu(Update update, Language lan, BotState state) {
        if (!update.getCallbackQuery().getData().contains("BACK"))
            saveHomeDistrict(update, state);
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_DISTRICT)), lan);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(WRITE_ADDRESS, lan));
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage giveHomeTypeMenuSend(Update update, Language lan, BotState state) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        if (!update.hasCallbackQuery() && !update.getMessage().hasLocation())
            saveHomeAddress(update, state);

        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(HOUSE, FLAT),
                List.of(message.hasLocation() ? BACK_SEND_LOCATION : BACK_TO_GIVE_ADDRESS)
        ), lan);
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(CHOOSE_ACCOMMODATION_TYPE_MENU_TEXT, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText giveHomeTypeMenuEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(HOUSE, FLAT),
                List.of(message.hasLocation() ? BACK_SEND_LOCATION : BACK_TO_GIVE_ADDRESS)
        ), lan);
        EditMessageText sendMessage = new EditMessageText(getWord(CHOOSE_ACCOMMODATION_TYPE_MENU_TEXT, lan));
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static boolean checkLocation(Update update) {
        if (!update.hasMessage()) return true;
        Message message = update.getMessage();
        if (!message.hasLocation()) return true;
        Location location = message.getLocation();
        LocationsItem data = LocationService.getData(location.getLatitude(), location.getLongitude());
        if (data != null) {
            Home home = new Home();
            home.setAddress(data.getStreet());
            try {
                Region region = Region.valueOf(data.getAdminArea5().toUpperCase());
                for (District value : District.values())
                    if(value.getRegionId()==region.getId()){
                        home.setDistrict(value);
                        break;
                    }
                home.setRegion(region);
            } catch (Exception e) {
                return false;
            }

            home.setMapUrl(data.getMapUrl());
            HomeService.saveHomeByLocation(home, message.getChatId().toString());
            return true;
        }
        return false;
    }

    public static SendMessage LocationNotFound(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        return new SendMessage(message.getChatId().toString(), getWord(LOCATION_NOT_FOUND, lan));
    }

    public static EditMessageText giveHomeNumberMenu(Update update, Language lan, BotState state) {
        if (state.equals(BotState.GIVE_HOME_NUMBER) && update.hasCallbackQuery() && !update.getCallbackQuery().getData().contains("BACK"))
            saveHomeType(update, state);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(NUMBER_OF_ROOMS, lan));
        editMessageText.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_TYPE)), lan));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    public static SendMessage giveHomeNumberMenuSend(Update update, Language lan, BotState state) {
        if (state.equals(BotState.GIVE_HOME_NUMBER))
            saveHomeType(update, state);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(NUMBER_OF_ROOMS, lan));
        sendMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_TYPE)), lan));
        return sendMessage;
    }

    public static SendMessage giveHomeAreaMenuSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(ENTER_AREA_MENU_TEXT, lan));
        sendMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_NUMBER)), lan));
        return sendMessage;
    }

    public static EditMessageText giveHomeAreaMenuEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(ENTER_AREA_MENU_TEXT, lan));
        editMessageText.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_NUMBER)), lan));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    public static SendMessage giveHomePhotoMenuSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(SEND_PHOTO_MENU_TEXT, lan));
        sendMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_AREA)), lan));
        return sendMessage;
    }

    public static EditMessageText giveHomePhotoMenuEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(SEND_PHOTO_MENU_TEXT, lan));
        editMessageText.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_AREA)), lan));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    public static SendMessage giveHomePriceMenuSend(Update update, Language lan, BotState state) {
        if (state.equals(BotState.GIVE_HOME_PRICE_SEND) && !update.hasCallbackQuery())
            saveHomePhoto(update, state);
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(ENTER_PRICE_MENU_TEXT, lan));
        sendMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_PHOTO)), lan));
        return sendMessage;
    }

    public static EditMessageText giveHomePriceMenuEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessage = new EditMessageText(getWord(ENTER_PRICE_MENU_TEXT, lan));
        editMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_PHOTO)), lan));
        editMessage.setChatId(message.getChatId().toString());
        editMessage.setMessageId(message.getMessageId());
        return editMessage;
    }

    public static SendMessage giveHomeDescription(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(WRITE_DESCRIPTION_MENU_TEXT, lan));
        sendMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_GIVE_HOME_PRICE)), lan));
        return sendMessage;
    }

    public static SendMessage successfullySaved(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        return new SendMessage(message.getChatId().toString(), getWord(SUCCESSFULLY_SAVED, lan));
    }

    public static EditMessageText getShowMenuEdit(Update update, Language lan) {
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(SEARCH, SHOW_ALL),
                List.of(BACK_TO_MAIN_MENU_EDIT)
        ), lan);

        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(CHOOSE_ACTION, lan));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage getShowMenuSend(Update update, Language lan) {
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(SEARCH, SHOW_ALL),
                List.of(BACK_TO_MAIN_MENU_EDIT)
        ), lan);

        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(CHOOSE_ACTION, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static List<SendPhoto> showAllHomes(Update update, Language lan) {
        InlineKeyboardMarkup markup = null;
        List<List<InlineKeyboardButton>> rowList = null;
        List<InlineKeyboardButton> row1 = null;
        List<InlineKeyboardButton> row2 = null;
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        List<Home> allHome = HomeService.getAllHome();
        if (allHome.isEmpty())
            return null;

        User user = UserService.getByChatId(message.getChatId().toString());
        List<SendPhoto> sendMessageList = new ArrayList<>();
        SendPhoto sendPhoto = null;

        for (Home home : allHome) {
            if (!home.isActive()) continue;
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

            Like like = LikeService.getLikeByHomeIdAndUserId(home.getId(), user.getId());

            InlineKeyboardButton phoneButton =
                    new InlineKeyboardButton(InterestService.getVisible(home.getId(), user.getId()) ?
                            UserService.getById(home.getOwnerId()).getPhoneNumber() : getWord(GET_PHONE_NUMBER, lan));
            phoneButton.setCallbackData(home.getId().toString());


            InlineKeyboardButton likeButton = new InlineKeyboardButton(home.getLikes()
                    + " " + (like.isActive() ? LIKE_ACTIVE : LIKE_NOT_ACTIVE));
            likeButton.setCallbackData(like.getId().toString());
            row1.add(phoneButton);
            row2.add(likeButton);
            sendPhoto.setReplyMarkup(markup);
        }

        InlineKeyboardButton menu = new InlineKeyboardButton(getWord(MENU, lan));
        menu.setCallbackData(BACK_TO_MAIN_MENU_SEND);

        InlineKeyboardButton back = new InlineKeyboardButton(getWord(BACK, lan));
        back.setCallbackData(BACK_TO_SHOW_MENU_SEND);
        assert row2 != null;
        row2.add(0, back);
        row2.add(menu);
        sendPhoto.setReplyMarkup(markup);
        sendMessageList.add(sendPhoto);
        return sendMessageList;
    }

    public static SendMessage homeNotFound(Update update, Language lan, String back) {
        InlineKeyboardButton backButton = new InlineKeyboardButton(getWord(BACK, lan));
        backButton.setCallbackData(back);
        InlineKeyboardButton menu = new InlineKeyboardButton(getWord(MENU, lan));
        menu.setCallbackData(BACK_TO_MAIN_MENU_SEND);
        List<InlineKeyboardButton> buttonList = new ArrayList<>();
        buttonList.add(backButton);
        if(!back.equals(BACK_TO_ADMIN_HOMES_FILTER))
        buttonList.add(menu);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(buttonList);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(HOMES_NOT_FOUND, lan));
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }

    public static EditMessageCaption changeVisibleHomePhone(Update update, Language lan, String backName, BotState state) {
        Message message = update.getCallbackQuery().getMessage();
        Home home = HomeService.getById(UUID.fromString(update.getCallbackQuery().getData()));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        markup.setKeyboard(rowList);
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        rowList.add(row1);
        rowList.add(row2);

        User user = UserService.getByChatId(message.getChatId().toString());
        Like like = LikeService.getLikeByHomeIdAndUserId(home.getId(), user.getId());

        InlineKeyboardButton phoneButton =
                new InlineKeyboardButton(InterestService.changeVisible(home.getId(), user.getId()) ?
                        UserService.getById(home.getOwnerId()).getPhoneNumber() : getWord(GET_PHONE_NUMBER, lan));
        phoneButton.setCallbackData(home.getId().toString());


        InlineKeyboardButton likeButton = new InlineKeyboardButton(home.getLikes()
                + " " + (like.isActive() ? LIKE_ACTIVE : LIKE_NOT_ACTIVE));
        likeButton.setCallbackData(like.getId().toString());

        InlineKeyboardButton back = new InlineKeyboardButton(getWord(BACK, lan));
        back.setCallbackData(backName);
        InlineKeyboardButton menu = new InlineKeyboardButton(getWord(MENU, lan));
        menu.setCallbackData(BACK_TO_MAIN_MENU_SEND);

        row1.add(phoneButton);

        if (state.equals(BotState.CHANGE_HOME_LIKE_MENU_MY_ACCOMMODATIONS) || state.equals(BotState.SHOW_HOME_PHONE_MENU_MY_ACCOMMODATIONS)) {
            InlineKeyboardButton deleteButton = new InlineKeyboardButton(DELETE);
            deleteButton.setCallbackData(DELETE + home.getId());
            row1.add(likeButton);
            row2.add(back);
            row2.add(deleteButton);
            row2.add(menu);
        } else {
            row2.add(back);
            row2.add(likeButton);
            row2.add(menu);
        }


        EditMessageCaption editMessageCaption = new EditMessageCaption();
        editMessageCaption.setMessageId(message.getMessageId());
        editMessageCaption.setChatId(message.getChatId().toString());
        editMessageCaption.setCaption(getCaptionByHome(home, lan));
        editMessageCaption.setReplyMarkup(markup);
        return editMessageCaption;
    }

    /////////// CAPTION
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
                getWord(DESCRIPTION, lan) + home.getDescription();
    }

    public static EditMessageCaption changeHomeLike(Update update, Language lan, String backName, BotState state) {
        Message message = update.getCallbackQuery().getMessage();
        Like like = LikeService.getById(UUID.fromString(update.getCallbackQuery().getData()));
        User user = UserService.getByChatId(message.getChatId().toString());
        Home home = HomeService.getById(like.getHomeId());
        like = LikeService.changeLike(home.getId(), user.getId());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        markup.setKeyboard(rowList);
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        rowList.add(row1);
        rowList.add(row2);


        InlineKeyboardButton phoneButton =
                new InlineKeyboardButton(InterestService.getVisible(home.getId(), user.getId()) ?
                        UserService.getById(home.getOwnerId()).getPhoneNumber() : getWord(GET_PHONE_NUMBER, lan));
        phoneButton.setCallbackData(home.getId().toString());


        InlineKeyboardButton likeButton = new InlineKeyboardButton(home.getLikes()
                + " " + (like.isActive() ? LIKE_ACTIVE : LIKE_NOT_ACTIVE));
        likeButton.setCallbackData(like.getId().toString());

        InlineKeyboardButton back = new InlineKeyboardButton(getWord(BACK, lan));
        back.setCallbackData(backName);

        InlineKeyboardButton menu = new InlineKeyboardButton(getWord(MENU, lan));
        menu.setCallbackData(BACK_TO_MAIN_MENU_SEND);

        row1.add(phoneButton);
        if (state.equals(BotState.CHANGE_HOME_LIKE_MENU_MY_ACCOMMODATIONS) || state.equals(BotState.SHOW_HOME_PHONE_MENU_MY_ACCOMMODATIONS)) {
            InlineKeyboardButton deleteButton = new InlineKeyboardButton(DELETE);
            deleteButton.setCallbackData(DELETE + home.getId());
            row1.add(likeButton);
            row2.add(back);
            row2.add(deleteButton);
            row2.add(menu);
        } else {
            row2.add(back);
            row2.add(likeButton);
            row2.add(menu);
        }


        EditMessageCaption editMessageCaption = new EditMessageCaption();
        editMessageCaption.setMessageId(message.getMessageId());
        editMessageCaption.setChatId(message.getChatId().toString());
        editMessageCaption.setCaption(getCaptionByHome(home, lan));
        editMessageCaption.setReplyMarkup(markup);
        return editMessageCaption;
    }

    public static EditMessageText chooseRegionMenu(Update update, Language lan) {
        CallbackQuery query = update.getCallbackQuery();
        Message message = query.getMessage();
        saveSearchRegion(update);
        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (Region value : Region.values()) {
            if (row.size() != 3) {
                row.add(value.name());
            } else {
                rows.add(row);
                row = new ArrayList<>();
                row.add(value.name());
            }
        }
        rows.add(row);
        rows.add(List.of(BACK_TO_SHOW_MENU_EDIT, SKIP));
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(rows, lan);
        EditMessageText editMessageText = new EditMessageText(getWord(CHOOSE_REGION_MENU_TEXT, lan));
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    // GET STATE
    public static BotState getState(Update update, BotState state) {
        String data = update.getCallbackQuery().getData();
        if (isRegion(update)) {
            if (state.equals(BotState.GIVE_REGION))
                return BotState.GIVE_DISTRICT;
            else
                return BotState.CHOOSE_DISTRICT;
        }
        if (isHomeType(update))
            return state.equals(BotState.CHOOSE_HOME_TYPE) ? BotState.CHOOSE_HOME_NUMBER_EDIT : BotState.GIVE_HOME_NUMBER;
        if (LikeService.isLike(data))
            return state.equals(BotState.SHOW_OPTIONS) ? BotState.CHANGE_HOME_LIKE_MENU_ALL : state.equals(BotState.SHOW_SORTED_OPTIONS) ?
                    BotState.CHANGE_HOME_LIKE_MENU_SEARCH : state.equals(BotState.MY_FAVOURITES) ? BotState.CHANGE_HOME_LIKE_MENU_FAVOURITES :
                    state.equals(BotState.MY_HOMES) ? BotState.CHANGE_HOME_LIKE_MENU_MY_ACCOMMODATIONS : BotState.CHANGE_HOME_LIKE_MENU_ALL;
        if (HomeService.checkById(data))
            return state.equals(BotState.SHOW_OPTIONS) ? BotState.SHOW_HOME_PHONE_MENU_ALL : state.equals(BotState.SHOW_SORTED_OPTIONS) ?
                    BotState.SHOW_HOME_PHONE_MENU_SEARCH : state.equals(BotState.MY_FAVOURITES) ? BotState.SHOW_HOME_PHONE_MENU_FAVOURITES :
                    state.equals(BotState.MY_HOMES) ? BotState.SHOW_HOME_PHONE_MENU_MY_ACCOMMODATIONS : BotState.SHOW_HOME_PHONE_MENU_ALL;

        return data.startsWith(DELETE) ? BotState.DELETE_ACCOMMODATION : state.equals(BotState.CHOOSE_DISTRICT) ? BotState.CHOOSE_HOME_STATUS :
                BotState.GIVE_ADDRESS;
    }

    public static BotState getStateBySkip(Update update, BotState state) {
        switch (state) {
            case CHOOSE_REGION -> {
                saveSearchRegion(update);
                state = BotState.CHOOSE_HOME_STATUS;
            }
            case CHOOSE_DISTRICT -> state = BotState.CHOOSE_HOME_STATUS;
            case CHOOSE_HOME_STATUS -> state = BotState.CHOOSE_HOME_TYPE;
            case CHOOSE_HOME_TYPE -> state = BotState.CHOOSE_HOME_NUMBER_EDIT;
            case CHOOSE_HOME_NUMBER_EDIT, CHOOSE_HOME_NUMBER_SEND -> state = BotState.CHOOSE_HOME_MIN_PRICE_EDIT;
            case CHOOSE_HOME_MIN_PRICE_SEND, CHOOSE_HOME_MIN_PRICE_EDIT -> state = BotState.CHOOSE_HOME_MAX_PRICE_EDIT;
            case CHOOSE_HOME_MAX_PRICE_SEND, CHOOSE_HOME_MAX_PRICE_EDIT -> state = BotState.SHOW_SORTED_OPTIONS;
            default -> state = BotState.ERROR;
        }

        return state;
    }
    //


    public static EditMessageText chooseDistrict(Update update, Language lan) {
        CallbackQuery query = update.getCallbackQuery();
        Message message = query.getMessage();

        Region region;
        if (!query.getData().contains("BACK"))
            region = saveSearchRegion(update);
        else {
            saveSearchDistrict(update);
            Search search = SearchService.getSearch(UserService.getByChatId(message.getChatId().toString()).getId());
            if (search == null || search.getRegion() == null) return chooseRegionMenu(update, lan);
            region = search.getRegion();
        }

        List<List<String>> rows = new ArrayList<>();
        List<String> row = new ArrayList<>();
        for (District value : District.values())
            if (value.getRegionId() == region.getId()) {
                if (row.size() != 3) {
                    row.add(value.name());
                } else {
                    rows.add(row);
                    row = new ArrayList<>();
                    row.add(value.name());
                }
            }
        rows.add(row);
        rows.add(List.of(BACK_TO_CHOOSE_REGION, SKIP));
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(rows, lan);
        EditMessageText editMessageText = new EditMessageText(getWord(CHOOSE_DISTRICT_MENU_TEXT, lan));
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;
    }

    public static EditMessageText chooseHomeStatus(Update update, Language lan) {
        CallbackQuery query = update.getCallbackQuery();
        if (query.getData().contains("BACK"))
            saveSearchStatus(update);
        else
            saveSearchDistrict(update);


        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(GET_RENTING, FOR_BUY),
                List.of(BACK_TO_CHOOSE_DISTRICT, SKIP)
        ), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(SET_ACCOMMODATION_FOR_MENU_TEXT, lan));
        editMessageText.setReplyMarkup(markup);
        editMessageText.setChatId(query.getMessage().getChatId().toString());
        editMessageText.setMessageId(query.getMessage().getMessageId());
        return editMessageText;
    }

    public static EditMessageText chooseHomeType(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        CallbackQuery query = update.getCallbackQuery();
        if (update.hasCallbackQuery()) {
            if (query.getData().contains("BACK"))
                saveSearchType(update);
            else
                saveSearchStatus(update);
        }

        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(HOUSE, FLAT),
                List.of(BACK_TO_CHOOSE_HOME_STATUS, SKIP)
        ), lan);
        EditMessageText sendMessage = new EditMessageText(getWord(CHOOSE_ACCOMMODATION_TYPE_MENU_TEXT, lan));
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText chooseHomeNumberEdit(Update update, Language lan) {
        CallbackQuery query = update.getCallbackQuery();
        if (update.hasCallbackQuery()) {
            if (query.getData().contains("BACK"))
                saveSearchNumber(update);
            else
                saveSearchType(update);
        }
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessageText = new EditMessageText(getWord(NUMBER_OF_ROOMS, lan));
        editMessageText.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_CHOOSE_HOME_TYPE, SKIP)), lan));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        return editMessageText;

    }

    public static SendMessage chooseHomeNumberSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(NUMBER_OF_ROOMS, lan));
        sendMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_CHOOSE_HOME_TYPE, SKIP)), lan));
        return sendMessage;
    }

    public static SendMessage chooseMinPriceMenuSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(MIN_PRICE, lan));
        sendMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_CHOOSE_HOME_NUMBER, SKIP)), lan));
        return sendMessage;
    }

    public static EditMessageText chooseMinPriceMenuEdit(Update update, Language lan) {
        CallbackQuery query = update.getCallbackQuery();
        if (update.hasCallbackQuery()) {
            if (query.getData().contains("BACK"))
                saveSearchMinPrice(update);
            if (query.getData().equals(SKIP))
                saveSearchNumber(update);
        }
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessage = new EditMessageText(getWord(MIN_PRICE, lan));
        editMessage.setChatId(message.getChatId().toString());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_CHOOSE_HOME_NUMBER, SKIP)), lan));
        return editMessage;
    }

    public static SendMessage chooseMaxPriceMenuSend(Update update, Language lan) {
        CallbackQuery query = update.getCallbackQuery();
        if (update.hasCallbackQuery()) {
            if (query.getData().contains("BACK"))
                saveSearchMaxPrice(update);
            if (query.getData().equals(SKIP))
                saveSearchMinPrice(update);
        }
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(MAX_PRICE, lan));
        sendMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_CHOOSE_MIN_PRICE, SKIP)), lan));
        return sendMessage;
    }

    public static EditMessageText chooseMaxPriceMenuEdit(Update update, Language lan) {
        CallbackQuery query = update.getCallbackQuery();
        if (update.hasCallbackQuery()) {
            if (query.getData().contains("BACK"))
                saveSearchMaxPrice(update);
            if (query.getData().equals(SKIP))
                saveSearchMinPrice(update);
        }
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        EditMessageText editMessage = new EditMessageText(getWord(MAX_PRICE, lan));
        editMessage.setChatId(message.getChatId().toString());
        editMessage.setMessageId(message.getMessageId());
        editMessage.setReplyMarkup(InlineKeyboardService.createMarkup(List.of(List.of(BACK_TO_CHOOSE_MIN_PRICE, SKIP)), lan));
        return editMessage;
    }

    public static List<SendPhoto> showSortedOptionsSend(Update update, Language lan) {
        InlineKeyboardMarkup markup = null;
        List<List<InlineKeyboardButton>> rowList;
        List<InlineKeyboardButton> row1;
        List<InlineKeyboardButton> row2 = null;
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        Search search = SearchService.getSearch(UserService.getByChatId(message.getChatId().toString()).getId());
        List<Home> allHome = SearchService.searchHome(search);
        if (allHome.isEmpty())
            return null;

        User user = UserService.getByChatId(message.getChatId().toString());
        List<SendPhoto> sendMessageList = new ArrayList<>();
        SendPhoto sendPhoto = null;

        for (Home home : allHome) {
            if (!home.isActive()) continue;
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

            Like like = LikeService.getLikeByHomeIdAndUserId(home.getId(), user.getId());

            InlineKeyboardButton phoneButton =
                    new InlineKeyboardButton(InterestService.getVisible(home.getId(), user.getId()) ?
                            UserService.getById(home.getOwnerId()).getPhoneNumber() : getWord(GET_PHONE_NUMBER, lan));
            phoneButton.setCallbackData(home.getId().toString());


            InlineKeyboardButton likeButton = new InlineKeyboardButton(home.getLikes()
                    + " " + (like.isActive() ? LIKE_ACTIVE : LIKE_NOT_ACTIVE));
            likeButton.setCallbackData(like.getId().toString());
            row1.add(phoneButton);
            row2.add(likeButton);
            sendPhoto.setReplyMarkup(markup);
        }

        InlineKeyboardButton back = new InlineKeyboardButton(getWord(BACK, lan));
        back.setCallbackData(BACK_TO_CHOOSE_MAX_PRICE_SEND);
        InlineKeyboardButton menu = new InlineKeyboardButton(getWord(MENU, lan));
        menu.setCallbackData(BACK_TO_MAIN_MENU_SEND);
        assert row2 != null;
        row2.add(0, back);
        row2.add(menu);
        sendPhoto.setReplyMarkup(markup);
        sendMessageList.add(sendPhoto);
        return sendMessageList;
    }

    public static EditMessageText getMyNotesMenuEdit(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MY_ACCOMMODATIONS, MY_FAVOURITES),
                List.of(BACK_TO_MAIN_MENU_EDIT)
        ), lan);
        EditMessageText editMessageText = new EditMessageText(getWord(CHOOSE_ACTION, lan));
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setReplyMarkup(markup);
        return editMessageText;
    }

    public static SendMessage getMyNotesMenuSend(Update update, Language lan) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MY_ACCOMMODATIONS, MY_FAVOURITES),
                List.of(BACK_TO_MAIN_MENU_EDIT)
        ), lan);
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), getWord(CHOOSE_ACTION, lan));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static List<SendPhoto> getMyFavouriteHomes(Update update, Language lan) {
        InlineKeyboardMarkup markup = null;
        List<List<InlineKeyboardButton>> rowList;
        List<InlineKeyboardButton> row1;
        List<InlineKeyboardButton> row2 = null;
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();

        User user = UserService.getByChatId(message.getChatId().toString());
        List<Home> allHomes = HomeService.getLikedHomesByUserId(user.getId());
        if (allHomes.isEmpty())
            return null;

        List<SendPhoto> sendMessageList = new ArrayList<>();
        SendPhoto sendPhoto = null;

        for (Home home : allHomes) {
            if (!home.isActive()) continue;
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

            Like like = LikeService.getLikeByHomeIdAndUserId(home.getId(), user.getId());

            InlineKeyboardButton phoneButton =
                    new InlineKeyboardButton(InterestService.getVisible(home.getId(), user.getId()) ?
                            UserService.getById(home.getOwnerId()).getPhoneNumber() : getWord(GET_PHONE_NUMBER, lan));
            phoneButton.setCallbackData(home.getId().toString());


            InlineKeyboardButton likeButton = new InlineKeyboardButton(home.getLikes()
                    + " " + (like.isActive() ? LIKE_ACTIVE : LIKE_NOT_ACTIVE));
            likeButton.setCallbackData(like.getId().toString());
            row1.add(phoneButton);
            row2.add(likeButton);
            sendPhoto.setReplyMarkup(markup);
        }

        InlineKeyboardButton back = new InlineKeyboardButton(getWord(BACK, lan));
        back.setCallbackData(BACK_TO_MY_NOTES_MENU_SEND);
        InlineKeyboardButton menu = new InlineKeyboardButton(getWord(MENU, lan));
        menu.setCallbackData(BACK_TO_MAIN_MENU_SEND);
        assert row2 != null;
        row2.add(0, back);
        row2.add(menu);
        sendPhoto.setReplyMarkup(markup);
        sendMessageList.add(sendPhoto);
        return sendMessageList;
    }

    public static List<SendPhoto> getMyHomes(Update update, Language lan) {
        InlineKeyboardMarkup markup = null;
        List<List<InlineKeyboardButton>> rowList;
        List<InlineKeyboardButton> row1;
        List<InlineKeyboardButton> row2 = null;
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();

        User user = UserService.getByChatId(message.getChatId().toString());
        List<Home> allHomes = HomeService.getByOwnerId(user.getId());
        if (allHomes.isEmpty())
            return null;

        List<SendPhoto> sendMessageList = new ArrayList<>();
        SendPhoto sendPhoto = null;

        for (Home home : allHomes) {
            if (!home.isActive()) continue;
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

            Like like = LikeService.getLikeByHomeIdAndUserId(home.getId(), user.getId());

            InlineKeyboardButton phoneButton =
                    new InlineKeyboardButton(InterestService.getVisible(home.getId(), user.getId()) ?
                            UserService.getById(home.getOwnerId()).getPhoneNumber() : getWord(GET_PHONE_NUMBER, lan));
            phoneButton.setCallbackData(home.getId().toString());

            InlineKeyboardButton likeButton = new InlineKeyboardButton(home.getLikes()
                    + " " + (like.isActive() ? LIKE_ACTIVE : LIKE_NOT_ACTIVE));
            likeButton.setCallbackData(like.getId().toString());

            InlineKeyboardButton deleteButton = new InlineKeyboardButton(DELETE);
            deleteButton.setCallbackData(DELETE + home.getId());


            row1.add(phoneButton);
            row1.add(likeButton);
            row2.add(deleteButton);
            sendPhoto.setReplyMarkup(markup);
        }

        InlineKeyboardButton back = new InlineKeyboardButton(getWord(BACK, lan));
        back.setCallbackData(BACK_TO_MY_NOTES_MENU_SEND);
        InlineKeyboardButton menu = new InlineKeyboardButton(getWord(MENU, lan));
        menu.setCallbackData(BACK_TO_MAIN_MENU_SEND);
        assert row2 != null;
        row2.add(0, back);
        row2.add(menu);
        sendPhoto.setReplyMarkup(markup);
        sendMessageList.add(sendPhoto);
        return sendMessageList;
    }

    public static DeleteMessage deleteAccommodation(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        HomeService.deleteHome(UUID.fromString(query.getData().substring(DELETE.length())));
        return deleteMessage(update);
    }


    public static void changeIsAdminUser(Update update,boolean isAdmin) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        UserService.changeIsAdmin(message.getChatId().toString(),isAdmin);
    }
}
