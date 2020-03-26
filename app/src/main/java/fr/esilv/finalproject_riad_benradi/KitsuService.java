package fr.esilv.finalproject_riad_benradi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KitsuService {
    @GET("anime")
    Call<KitsuSearchResponse> groupList(
            @Query("filter[text]") String filter);

    @GET("streaming-links")
    Call<KitsuSearchResponse> GetstreamingLinks();

}
