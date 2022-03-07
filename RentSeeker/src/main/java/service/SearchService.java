package service;

import model.Home;
import model.Search;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SearchService {
    static List<Search> searchList;

    public static void getSearchesFromBack(){
        List<Search> searches = FileService.getSearch();
        searchList = Objects.requireNonNullElseGet(searches, ArrayList::new);
    }

    public static Search getSearch(UUID userId){
        for (Search current : searchList) {
            if(userId.equals(current.getUserId())){
                return current;
            }
        }
        return null;
    }

    public static void addSearch(Search search){
        boolean cantFind = true;
        int index = 0;
        for (Search current : searchList) {
            if(search.getUserId().equals(current.getUserId())){
                cantFind = false;
                searchList.set(index,search);
            }
            index++;
        }
        if (cantFind)
            searchList.add(search);
        FileService.setSearch(searchList);
    }

    public static List<Home> searchHome(Search search){
        List<Home> found = new ArrayList<>();
        for (Home home : HomeService.getAllHome()) {
            if (search.getRegion() != null)
                if (search.getRegion().equals(home.getRegion())){
                    if (search.getDistrict() != null)
                        if (!search.getDistrict().equals(home.getDistrict()))
                            continue;
                }else continue;

            if (search.getHomeType() != null)
                if (!home.getHomeType().equals(search.getHomeType()))
                    continue;
            if (search.getStatus()!=null)
                if (!search.getStatus().equals(home.getStatus()))
                    continue;
            if (search.getNumberOfRooms()!=-1)
                if (search.getNumberOfRooms()!=home.getNumberOfRooms())
                    continue;
            if(search.getMinPrice()!=-1)
                if(search.getMinPrice()>home.getPrice())
                    continue;
            if(search.getMaxPrice()!=-1)
                if(search.getMaxPrice()<home.getPrice())
                    continue;
            found.add(home);
        }
        return found;
    }


}
