package pro.ghosh.sosapp.repos;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pro.ghosh.sosapp.services.SmsService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SmsRepository {

    private SmsRepository instance;

    private SmsService smsService;

    public SmsRepository getInstance() {
        if (instance == null)
            instance = new SmsRepository();
        return instance;
    }

    public SmsRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://objective-dijkstra-d6f53a.netlify.app/.netlify/functions/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        smsService = retrofit.create(SmsService.class);
    }

    public SmsService getSmsService() {
        return smsService;
    }
}
