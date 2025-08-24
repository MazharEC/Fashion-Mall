package com.appsv.fashionmall.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.fashionmall.common.HomeScreenState
import com.appsv.fashionmall.common.ResultState
import com.appsv.fashionmall.domain.models.CartDataModels
import com.appsv.fashionmall.domain.models.CategoryDataModels
import com.appsv.fashionmall.domain.models.ProductDataModels
import com.appsv.fashionmall.domain.models.UserData
import com.appsv.fashionmall.domain.models.UserDataParent
import com.appsv.fashionmall.domain.useCase.AddToCartUseCase
import com.appsv.fashionmall.domain.useCase.AddToFavUseCase
import com.appsv.fashionmall.domain.useCase.GetAllCategoriesUseCase
import com.appsv.fashionmall.domain.useCase.GetAllFavUseCase
import com.appsv.fashionmall.domain.useCase.GetAllProductsUseCase
import com.appsv.fashionmall.domain.useCase.GetAllSuggestedProductsUseCase
import com.appsv.fashionmall.domain.useCase.GetBannerUseCase
import com.appsv.fashionmall.domain.useCase.GetCartUseCase
import com.appsv.fashionmall.domain.useCase.GetCategoriesInLimitedUseCase
import com.appsv.fashionmall.domain.useCase.GetCheckoutUseCase
import com.appsv.fashionmall.domain.useCase.GetProductByIdUseCase
import com.appsv.fashionmall.domain.useCase.GetProductsInLimitedUseCase
import com.appsv.fashionmall.domain.useCase.GetSpecificCategoryItemsUseCase
import com.appsv.fashionmall.domain.useCase.GetUserByIdUseCase
import com.appsv.fashionmall.domain.useCase.LoginUserUseCase
import com.appsv.fashionmall.domain.useCase.RegisterUserUseCase
import com.appsv.fashionmall.domain.useCase.UpdateUserDataUseCase
import com.appsv.fashionmall.domain.useCase.UploadUserProfileImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


class FashionMallViewModel @Inject constructor(

    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUseCase: LoginUserUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val uploadUserProfileImageUseCase: UploadUserProfileImageUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getAllSuggestedProductsUseCase: GetAllSuggestedProductsUseCase,
    private val getSpecificCategoryItemsUseCase: GetSpecificCategoryItemsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val addToFavUseCase: AddToFavUseCase,
    private val getAllFavUseCase: GetAllFavUseCase,
    private val getCheckoutUseCase: GetCheckoutUseCase,
    private val getBannerUseCase: GetBannerUseCase,
    private val getProductsInLimitedUseCase: GetProductsInLimitedUseCase,
    private val getCategoriesInLimitedUseCase: GetCategoriesInLimitedUseCase

) : ViewModel() {

    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _updateScreenState = MutableStateFlow(UpdateScreenState())
    val updateScreenState = _updateScreenState.asStateFlow()

    private val _userProfileImageState = MutableStateFlow(UploadUserProfileImageState())
    val userProfileImageState = _userProfileImageState.asStateFlow()

    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState = _addToCartState.asStateFlow()

    private val _getProductByIdState = MutableStateFlow(GetProductByIdState())
    val getProductByIdState = _getProductByIdState.asStateFlow()

    private val _getAllProductsState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()

    private val _addToFavState = MutableStateFlow(AddToFavState())
    val addToFavState = _addToFavState.asStateFlow()

    private val _getAllFavState = MutableStateFlow(GetAllFavState())
    val getAllFavState = _getAllFavState.asStateFlow()

    private val _getCartState = MutableStateFlow(GetCartState())
    val getCartState = _getCartState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(GetAllCategoriesState())
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()

    private val _getCheckoutState = MutableStateFlow(GetCheckoutState())
    val getCheckoutState = _getCheckoutState.asStateFlow()

    private val _getSpecificCategoryItemsState = MutableStateFlow(GetSpecificCategoryItemsState())
    val getSpecificCategoryItemsState = _getSpecificCategoryItemsState.asStateFlow()

    private val _getAllSuggestedProductsState = MutableStateFlow(GetAllSuggestedProductsState())
    val getAllSuggestedProductsState = _getAllSuggestedProductsState.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()



    fun getSpecificCategoryItems(categoryName: String) {
        viewModelScope.launch {

            getSpecificCategoryItemsUseCase.getSpecificCategoryItems(categoryName).collect {
                when (it) {

                    is ResultState.Error -> {
                        _getSpecificCategoryItemsState.value = _getSpecificCategoryItemsState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getSpecificCategoryItemsState.value = _getSpecificCategoryItemsState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _getSpecificCategoryItemsState.value = _getSpecificCategoryItemsState.value.copy(
                            isLoading = false,
                            userData = it.data,
                            errorMessages = null // Clear any previous errors
                        )
                    }
                }
            }
        }
    }

    fun getCheckout(productId: String) {
        viewModelScope.launch {

            getCheckoutUseCase.getCheckout(productId).collect {

                when (it) {

                    is ResultState.Error -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }

    }

    fun getAllCategories() {
        viewModelScope.launch {

            getAllCategoriesUseCase.getAllCategories().collect {

                when (it) {

                    is ResultState.Error -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _getAllCategoriesState.value = _getAllCategoriesState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }

        }
    }

    fun getCart() {
        viewModelScope.launch {

            getCartUseCase.getCart().collect {

                when (it) {

                    is ResultState.Error -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {

            getAllProductsUseCase.getAllProducts().collect {

                when(it){

                    is ResultState.Error -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _getAllProductsState.value = _getAllProductsState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllFav() {
        viewModelScope.launch {

            getAllFavUseCase.getAllFav().collect{

                when(it){

                    is ResultState.Error -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }

        }
    }

    fun addToFav(productDataModels: ProductDataModels) {
        viewModelScope.launch {

            addToFavUseCase.addToFav(productDataModels).collect{

                when(it){

                    is ResultState.Error -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }

        }
    }

    fun getProductById(productId : String){
        viewModelScope.launch {

            getProductByIdUseCase.getProductById(productId).collect{

                when(it){

                    is ResultState.Error -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _getProductByIdState.value = _getProductByIdState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }

    }

    fun addToCart(cartDataModels: CartDataModels){
        viewModelScope.launch {

            addToCartUseCase.addToCart(cartDataModels).collect{

                when(it){

                    is ResultState.Error -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }

            }

        }
    }

    init {
        loadHomeScreenData()
    }

    private fun loadHomeScreenData() {
        viewModelScope.launch {

            combine(

                getCategoriesInLimitedUseCase.getCategoriesInLimited(),
                getProductsInLimitedUseCase.getProductsInLimited(),
                getBannerUseCase.getBanner()

            ) { categoriesResult, productsResult, bannerResult ->

                when{

                    categoriesResult is ResultState.Error -> {

                        HomeScreenState(isLoading = false, errorMessages = categoriesResult.message)
                    }
                    productsResult is ResultState.Error -> {

                        HomeScreenState(isLoading = false, errorMessages = productsResult.message)
                    }
                    bannerResult is ResultState.Error -> {

                        HomeScreenState(isLoading = false, errorMessages = bannerResult.message)
                    }

                    categoriesResult is ResultState.Success && productsResult is ResultState.Success && bannerResult is ResultState.Success -> {

                        HomeScreenState(
                            isLoading = false,
                            categories = categoriesResult.data,
                            products = productsResult.data,
                            banner = bannerResult.data
                        )
                    }

                    else -> {
                        HomeScreenState(isLoading = true)
                    }
                }
            }.collect {

                state -> _homeScreenState.value = state
            }
        }
    }

    fun upLoadUserProfileImage(uri: Uri){
        viewModelScope.launch {

            uploadUserProfileImageUseCase.uploadProfileImage(uri).collect{

                when(it){

                    is ResultState.Error -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _userProfileImageState.value = _userProfileImageState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun upDateUserData(userDataParent: UserDataParent) {

        viewModelScope.launch {

            updateUserDataUseCase.updateUserData(userDataParent = userDataParent).collect {

                when(it) {
                    is ResultState.Error -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun createUser(userData: UserData) {
        viewModelScope.launch {

            registerUserUseCase.registerUser(userData).collect {

                when(it){

                    is ResultState.Error -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun loginUser(userData: UserData){
        viewModelScope.launch {

            loginUseCase.loginUser(userData).collect{

                when(it){

                    is ResultState.Error -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getUserById(uid : String){
        viewModelScope.launch {

            getUserByIdUseCase.getUserById(uid).collect{

                when(it){

                    is ResultState.Error -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllSuggestedProducts(){
        viewModelScope.launch {

            getAllSuggestedProductsUseCase.getAllSuggestedProducts().collect{

                when(it){

                    is ResultState.Error -> {
                        _getAllSuggestedProductsState.value = _getAllSuggestedProductsState.value.copy(
                            isLoading = false,
                            errorMessages = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _getAllSuggestedProductsState.value = _getAllSuggestedProductsState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _getAllSuggestedProductsState.value = _getAllSuggestedProductsState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }

            }
        }

    }


}


data class ProfileScreenState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: UserDataParent? = null

)

data class SignUpScreenState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: String? = null

)

data class LoginScreenState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: String? = null
)

data class UpdateScreenState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: String? = null
)


data class UploadUserProfileImageState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: String? = null
)

data class AddToCartState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: String? = null
)

data class GetProductByIdState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: ProductDataModels? = null
)

data class AddToFavState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: String? = null
)

data class GetAllFavState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

data class GetAllProductsState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

data class GetCartState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: List<CartDataModels?> = emptyList()
)

data class GetAllCategoriesState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: List<CategoryDataModels?> = emptyList()
)

data class GetCheckoutState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: ProductDataModels? = null
)

data class GetSpecificCategoryItemsState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

data class GetAllSuggestedProductsState(
    val isLoading: Boolean = false,
    val errorMessages: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)
