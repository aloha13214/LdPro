package com.fsoc.template.common.di.module

import com.fsoc.template.data.api.ApiHelper
import com.fsoc.template.data.api.ApiHelperImplement
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.data.db.DatabaseHelperImplement
import com.fsoc.template.data.db.helper.message.detail.ChatDatabaseHelper
import com.fsoc.template.data.db.helper.message.detail.MessagesDatabaseImplement
import com.fsoc.template.data.db.helper.message.list.MessagesDatabaseHelper
import com.fsoc.template.data.db.helper.message.list.MessagesDatabaseHelperImplement
import com.fsoc.template.data.repository.BaseRepoImpl
import com.fsoc.template.domain.repository.BaseRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule() {

    @Provides
    @Singleton
    fun provideBaseRepo(repoImpl: BaseRepoImpl): BaseRepo {
        return repoImpl
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiHelperImplement: ApiHelperImplement): ApiHelper {
        return apiHelperImplement
    }

    @Provides
    @Singleton
    fun provideDatabaseHelper(databaseHelperImplement: DatabaseHelperImplement): DatabaseHelper {
        return databaseHelperImplement
    }

    @Provides
    @Singleton
    fun provideMessageDatabaseHelper(MessageDatabaseHelperImplement: MessagesDatabaseHelperImplement): MessagesDatabaseHelper {
        return MessageDatabaseHelperImplement
    }

    @Provides
    @Singleton
    fun provideMessagesDatabaseHelper(MessageDatabaseImplement: MessagesDatabaseImplement): ChatDatabaseHelper {
        return MessageDatabaseImplement
    }
}
