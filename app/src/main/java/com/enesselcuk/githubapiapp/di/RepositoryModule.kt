package com.enesselcuk.githubapiapp.di

import com.enesselcuk.githubapiapp.data.remote.service.GithubService
import com.enesselcuk.githubapiapp.data.repos.GithubRepositoryImpl
import com.enesselcuk.githubapiapp.domain.GithubRepos
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun providesRepository(
        api: GithubService,
        dispatcher: CoroutineDispatcher,
    ) = GithubRepositoryImpl(api, dispatcher)

//    @Provides
//    fun providesLocalRepository(
//        dao: BasketDao,
//        dispatcher: CoroutineDispatcher,
//    ) = LocalBasketRepositoryImpl(dao, dispatcher)

}

@Module
@InstallIn(ViewModelComponent::class)
interface Repos {
    @Binds
    fun basket(gameRepositoryImpl: GithubRepositoryImpl): GithubRepos
}

//@Module
//@InstallIn(ViewModelComponent::class)
//interface ReposLocal {
//    @Binds
//    fun basket(basketLocalRepositoryImpl: LocalBasketRepositoryImpl): LocalBasketRepos
//
//}

