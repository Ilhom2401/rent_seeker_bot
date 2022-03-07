package service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import util.constant.Constant;
import util.enums.Language;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardService extends LanguageService implements Constant {

    public static InlineKeyboardMarkup createMarkup(List<List<String>> rows, Language lan){
        List<List<InlineKeyboardButton>> rowList=new ArrayList<>();
        for (List<String> row : rows) {
            List<InlineKeyboardButton> dRow=new ArrayList<>();
            for (String word : row) {
                InlineKeyboardButton button;
                if(!word.contains("BACK")){
                    button=new InlineKeyboardButton(getWord(word,lan));
                    button.setCallbackData(word);
                }else {
                    button=new InlineKeyboardButton(getWord(BACK,lan));
                    button.setCallbackData(word);
                }
                dRow.add(button);
            }
            rowList.add(dRow);
        }

        return new InlineKeyboardMarkup(rowList);
    }
}
