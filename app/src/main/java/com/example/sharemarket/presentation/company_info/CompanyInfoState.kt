package com.example.sharemarket.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.sharemarket.domain.model.CompanyInfo
import com.example.sharemarket.domain.model.IntradayInfo
import kotlinx.coroutines.launch


data class CompanyInfoState(val stockInfos:List<IntradayInfo> = emptyList(),val company:CompanyInfo?=null,val isLoading:Boolean = false,val error:String? = null)
