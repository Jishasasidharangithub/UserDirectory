package com.example.userdirectory.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userdirectory.model.response.UserListResponseItem
import com.example.userdirectory.model.response.UserPostResponseItem
import com.example.userdirectory.network.NetworkResult
import com.example.userdirectory.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _usersList = MutableLiveData<NetworkResult<List<UserListResponseItem>>>()
    val usersList: MutableLiveData<NetworkResult<List<UserListResponseItem>>> get() = _usersList

    private val _usersPost = MutableLiveData<NetworkResult<List<UserPostResponseItem>>>()
    val usersPost: MutableLiveData<NetworkResult<List<UserPostResponseItem>>> get() = _usersPost

    fun usersList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.usersList().collect { result ->
                _usersList.postValue(result)
            }
        }
    }

    fun usersPost(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.usersPost(userId).collect { result ->
                _usersPost.postValue(result)
            }
        }
    }
}
