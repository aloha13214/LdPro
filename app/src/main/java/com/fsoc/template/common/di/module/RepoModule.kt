package com.fsoc.template.common.di.module

import com.fsoc.template.data.api.ApiHelper
import com.fsoc.template.data.api.ApiHelperImplement
import com.fsoc.template.data.db.DatabaseHelper
import com.fsoc.template.data.db.DatabaseHelperImplement
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
}
