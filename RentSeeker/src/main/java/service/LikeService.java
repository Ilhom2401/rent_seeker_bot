package service;

import model.Like;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LikeService {
    // only static method
    private static List<Like> likeList;

    public static void getLikeFromBack() throws IOException {
        List<Like> likes = FileService.getLikes();
        if (likes == null)
            likeList = new ArrayList<>();
        else
            likeList = likes;
    }


    public static Like changeLike(UUID homeId,UUID userId){
        for (int i = 0; i < likeList.size(); i++)
            if (likeList.get(i).getHomeId().equals(homeId) && likeList.get(i).getUserId().equals(userId)) {
                likeList.get(i).setActive(!likeList.get(i).isActive());
                HomeService.changeCountOfLike(likeList.get(i));
                FileService.setLikes(likeList);
                return likeList.get(i);
            }
        return null;
    }
    public static void saveLike(Like like){
        boolean cantFind = true;

        for (int i = 0; i < likeList.size(); i++)
            if (likeList.get(i).getHomeId().equals(like.getHomeId()) && likeList.get(i).getUserId().equals(like.getUserId())) {
                like.setActive(!like.isActive());
                likeList.set(i, like);
                cantFind = false;
                break;
            }

        if (cantFind)
            likeList.add(like);

        FileService.setLikes(likeList);
    }


    public static Like getLikeByHomeIdAndUserId(UUID homeId,UUID userId){
        for (Like like : likeList)
            if(like.getHomeId().equals(homeId) && like.getUserId().equals(userId))
                return like;
        Like like=new Like(homeId,userId);
        saveLike(like);
        return like;
    }

    public static boolean isLike(String data) {
        UUID uuid;
        try {
            uuid=UUID.fromString(data);
        }catch (Exception e){
            return false;
        }
        for (Like like : likeList)
            if(like.getId().equals(uuid))
                return true;
        return false;
    }

    public static Like getById(UUID id){
        for (Like like : likeList)
            if(like.getId().equals(id))
                return like;
        return null;
    }

    public static List<UUID> getActiveHomesByUserId(UUID userId){
        List<UUID> homeIdList=new ArrayList<>();
        for (Like like : likeList)
            if(like.getUserId().equals(userId) && like.isActive())
                homeIdList.add(like.getHomeId());
        return homeIdList;
    }
}
