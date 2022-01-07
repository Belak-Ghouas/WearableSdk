package com.psa.app.di

import com.psa.app.ui.MainActivityViewModel
import com.psa.app.ui.SendMessageUseCase
import com.psa.sdk.framework.*
import com.psa.sdk.util.Config
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Abdelhak GHOUAS
 * @email : abdelhak.ghouas@capgemini.com
 * Created 29/12/2021
 */

object ConfigImpl :Config{
    override fun getCapability(): String {
        return  "messages"
    }

    override fun getMessageRoute(): String {
        return "/messages"
    }

    override fun getDataPrefixPath(): String {
        return "/from_phone"
    }
}
val viewModels = module {
    viewModel{MainActivityViewModel(sendMessageUseCase = get())}
}
val NetworkDependency = module {
    single <Config>{ConfigImpl }
    single <ISender>{ Sender(context = get(), config = get())  }
    single <SenderDataSource> {SenderDataSourceImpl(sender = get())    }
    single <SenderRepository>{SenderRepositoryImpl(senderDataSource = get())  }
    single { SendMessageUseCase(senderRepository = get()) }
}