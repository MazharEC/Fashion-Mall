package com.appsv.fashionmall.domain.useCase

import android.net.Uri
import com.appsv.fashionmall.common.ResultState
import com.appsv.fashionmall.domain.models.BannerDataModels
import com.appsv.fashionmall.domain.models.CartDataModels
import com.appsv.fashionmall.domain.models.CategoryDataModels
import com.appsv.fashionmall.domain.models.ProductDataModels
import com.appsv.fashionmall.domain.models.UserData
import com.appsv.fashionmall.domain.models.UserDataParent
import com.appsv.fashionmall.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// User Authentication Use Cases
class RegisterUserUseCase @Inject constructor(private val repo: Repo) {

    fun registerUser(userData: UserData): Flow<ResultState<String>> {

        return repo.registerUserWithEmailAndPassword(userData)
    }
}

class LoginUserUseCase @Inject constructor(private val repo: Repo) {

    fun loginUser(userData: UserData): Flow<ResultState<String>> {

        return repo.loginUserWithEmailAndPassword(userData)
    }
}

class GetUserByIdUseCase @Inject constructor(private val repo: Repo) {

    fun getUserById(uid: String): Flow<ResultState<UserDataParent>> {

        return repo.getUserById(uid)
    }
}

class UpdateUserDataUseCase @Inject constructor(private val repo: Repo) {

    fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> {

        return repo.upDateUserData(userDataParent)
    }
}

class UploadUserProfileImageUseCase @Inject constructor(private val repo: Repo) {

    fun uploadProfileImage(uri: Uri): Flow<ResultState<String>> {

        return repo.userProfileImage(uri)
    }
}

// Product Use Cases
class GetProductsInLimitedUseCase @Inject constructor(private val repo: Repo) {

    fun getProductsInLimited(): Flow<ResultState<List<ProductDataModels>>> {

        return repo.getProductsInLimited()
    }
}

class GetAllProductsUseCase @Inject constructor(private val repo: Repo) {

    fun getAllProducts(): Flow<ResultState<List<ProductDataModels>>> {

        return repo.getAllProducts()
    }
}

class GetProductByIdUseCase @Inject constructor(private val repo: Repo) {

    fun getProductById(productId: String): Flow<ResultState<ProductDataModels>> {

        return repo.getProductById(productId)
    }
}

class GetAllSuggestedProductsUseCase @Inject constructor(private val repo: Repo) {

    fun getAllSuggestedProducts(): Flow<ResultState<List<ProductDataModels>>> {

        return repo.getAllSuggestedProducts()
    }
}

class GetSpecificCategoryItemsUseCase @Inject constructor(private val repo: Repo) {

    fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModels>>> {

        return repo.getSpecificCategoryItems(categoryName)
    }
}

// Category Use Cases
class GetCategoriesInLimitedUseCase @Inject constructor(private val repo: Repo) {

    fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModels>>> {

        return repo.getCategoriesInLimited()
    }
}

class GetAllCategoriesUseCase @Inject constructor(private val repo: Repo) {

    fun getAllCategories(): Flow<ResultState<List<CategoryDataModels>>> {

        return repo.getAllCategories()
    }
}

// Cart Use Cases
class AddToCartUseCase @Inject constructor(private val repo: Repo) {

    fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>> {

        return repo.addToCart(cartDataModels)
    }
}

class GetCartUseCase @Inject constructor(private val repo: Repo) {

    fun getCart(): Flow<ResultState<List<CartDataModels>>> {

        return repo.getCart()
    }
}

// Favorites Use Cases
class AddToFavUseCase @Inject constructor(private val repo: Repo) {

    fun addToFav(productDataModels: ProductDataModels): Flow<ResultState<String>> {

        return repo.addToFav(productDataModels)
    }
}

class GetAllFavUseCase @Inject constructor(private val repo: Repo) {

    fun getAllFav(): Flow<ResultState<List<ProductDataModels>>> {

        return repo.getAllFav()
    }
}

// Banner Use Cases
class GetBannerUseCase @Inject constructor(private val repo: Repo) {

    fun getBanner(): Flow<ResultState<List<BannerDataModels>>> {

        return repo.getBanner()
    }
}

// Checkout Use Cases
class GetCheckoutUseCase @Inject constructor(private val repo: Repo) {

    fun getCheckout(productId: String): Flow<ResultState<ProductDataModels>> {

        return repo.getCheckout(productId)
    }
}