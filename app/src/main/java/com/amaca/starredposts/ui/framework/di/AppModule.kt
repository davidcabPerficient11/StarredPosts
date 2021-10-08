package com.amaca.starredposts.ui.framework.di

import android.content.Context
import com.amaca.core.data.CommentRepository
import com.amaca.core.data.PostRepository
import com.amaca.core.data.UserRepository
import com.amaca.core.interactors.*
import com.amaca.starredposts.ui.framework.Interactors
import com.amaca.starredposts.ui.framework.data.db.CommentDao
import com.amaca.starredposts.ui.framework.data.db.PostDao
import com.amaca.starredposts.ui.framework.data.db.StarredPostsDatabase
import com.amaca.starredposts.ui.framework.data.db.UserDao
import com.amaca.starredposts.ui.framework.data.remote.PostApi
import com.amaca.starredposts.ui.framework.data.sources.CommentDataSourceImpl
import com.amaca.starredposts.ui.framework.data.sources.PostsDataSourceImpl
import com.amaca.starredposts.ui.framework.data.sources.UserDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    private val API_URL = "https://jsonplaceholder.typicode.com"

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(
            OkHttpClient.Builder()
//                .addInterceptor { chain -> // Retrofit escapes characters.
//                    val request = chain.request()
//                    val response = chain.proceed(request)
//                    response
//                }
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesStarredPostApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)


    //Database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) =
        StarredPostsDatabase.getInstance(appContext)

    //Daos
    @Provides
    @Singleton
    fun providesUserDao(starredPostsDatabase: StarredPostsDatabase) = starredPostsDatabase.userDao

    @Provides
    @Singleton
    fun providesPostDao(starredPostsDatabase: StarredPostsDatabase) = starredPostsDatabase.postDao

    @Provides
    @Singleton
    fun providesCommentDao(starredPostsDatabase: StarredPostsDatabase) =
        starredPostsDatabase.commentDao

    //DataSources

    @Provides
    @Singleton
    fun providesPostRepository(postDao: PostDao, postApi: PostApi) =
        PostRepository(PostsDataSourceImpl(postDao, postApi))


    @Provides
    @Singleton
    fun providesUserRepository(userDao: UserDao, postApi: PostApi) =
        UserRepository(UserDataSourceImpl(userDao, postApi))

    @Provides
    @Singleton
    fun providesCommentRepository(commentDao: CommentDao, postApi: PostApi) =
        CommentRepository(CommentDataSourceImpl(commentDao, postApi))


    //Saving some time, but you should separate interactors by their functions.


    @Provides
    @Singleton
    fun providesInteractors(
        userRepository: UserRepository,
        commentRepository: CommentRepository,
        postRepository: PostRepository
    ) = Interactors(
        AddComment(commentRepository),
        AddPost(postRepository),
        AddPostAndOverwrite(postRepository),
        AddPostAndOverwrite(postRepository),
        AddUser(userRepository),
        GetAllPosts(postRepository),
        GetAllFavoritePosts(postRepository),
        GetComment(commentRepository),
        GetUser(userRepository),
        RefreshPost(postRepository),
        RemoveAllComments(commentRepository),
        RemoveAllPost(postRepository),
        RemoveAllUsers(userRepository)
    )


}

