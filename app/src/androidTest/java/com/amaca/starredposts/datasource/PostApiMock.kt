package com.amaca.starredposts.datasource

import com.amaca.starredposts.ui.framework.data.db.CommentEntity
import com.amaca.starredposts.ui.framework.data.db.PostEntity
import com.amaca.starredposts.ui.framework.data.db.UserEntity
import com.amaca.starredposts.ui.framework.data.remote.PostApi
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response


internal val remoteValue = "remoteValue"
internal val remoteUserEntity = UserEntity(1, remoteValue, "2", "32", ".com")
internal val remotePostEntity = PostEntity(1,1,remoteValue, "body")
internal val remoteCommentEntity = CommentEntity(1, 1, "@com", remoteValue)


internal val localValue = "localValue"
internal val localPostEntity = PostEntity(1,1, localValue, "body")
internal val localUserEntity = UserEntity(1, localValue, "2", "32", ".com")
internal val localCommentEntity = CommentEntity(1, 1, "@com", localValue)


internal val api: PostApi = object: PostApi {
    val remotePostEntity2 = PostEntity(2, 1, remoteValue, "body")
    val remoteCommentEntity2 = CommentEntity(2, 1, "@com", remoteValue)

    override suspend fun getPosts(): Response<List<PostEntity>?> {
        return Response.success(listOf(remotePostEntity, remotePostEntity2))
    }

    override suspend fun getPostAuthor(id: Int): Response<UserEntity> {
        return Response.success(remoteUserEntity)
    }

    override suspend fun getPostComments(id: Int): Response<List<CommentEntity>> {
        return Response.success(listOf(remoteCommentEntity, remoteCommentEntity2))
    }

    override suspend fun getPost(id: Int): Response<PostEntity> {
        return Response.success(remotePostEntity)
    }
}

internal val apiUnavailable = object: PostApi {
    override suspend fun getPosts(): Response<List<PostEntity>?> {
        return Response.error(404, apiUnavailabeResponse)

    }

    override suspend fun getPostAuthor(id: Int): Response<UserEntity> {
        return Response.error(404, apiUnavailabeResponse )

    }

    override suspend fun getPostComments(id: Int): Response<List<CommentEntity>> {
        return Response.error(404, apiUnavailabeResponse )

    }

    override suspend fun getPost(id: Int): Response<PostEntity> {
        return Response.error(404, apiUnavailabeResponse )
    }
}

val apiUnavailabeResponse = ResponseBody.create(
    MediaType.parse("application/json"),
    "{\"key\":[\"somestuff\"]}"
)