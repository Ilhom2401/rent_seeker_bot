import bot.RentSeeker;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import service.*;


public class Main {
    public static void main(String[] args) throws Exception {
        TelegramBotsApi api=new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new RentSeeker());
        System.out.println("RENT_SEEKER IS STARTED!");
        UserService.getUserFromBack();
        HomeService.getHomeFromBack();
        LikeService.getLikeFromBack();
        InterestService.getInterestFromBack();
        SearchService.getSearchesFromBack();
    }
}
