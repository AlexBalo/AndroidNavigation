package com.balocco.androidnavigation.di

import com.balocco.androidcomponents.di.ApplicationScope
import com.balocco.androidnavigation.data.remote.*
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/* Module that contains dependencies for network operations. */
@Module
class NetworkModule {

    @Provides
    @ApplicationScope
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    @Provides
    @ApplicationScope
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Provides
    @ApplicationScope
    fun provideVersionInterceptor(): VersionInterceptor =
        VersionInterceptor(RemoteDataSource.LAST_API_VERSION)

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        versionInterceptor: VersionInterceptor
    ): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(versionInterceptor)
        .build()

    @Provides
    @ApplicationScope
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(RemoteDataSource.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    @ApplicationScope
    fun provideRemoteDataSource(retrofit: Retrofit):
            RemoteDataSource = retrofit.create(RemoteDataSource::class.java)

    @Provides
    @ApplicationScope
    fun providePicasso(): Picasso = Picasso.get()

    @Provides
    @ApplicationScope
    fun provideImageLoader(
        picasso: Picasso
    ): ImageLoader = PicassoImageLoader(picasso)
}