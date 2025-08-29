package io.proximety.hilitemall.network

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings.Secure
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.qualifiers.ApplicationContext
import io.proximety.hilitemall.model.SingleOrderResponse
import io.proximety.hilitemall.model.SpinGetRewardResponse
import io.proximety.hilitemall.model.UserQuizzesResponse
import io.proximety.hilitemall.model.enums.OnboardFlow
import io.proximety.hilitemall.model.events.EventDetailsResponse
import io.proximety.hilitemall.model.events.EventTicketsResponse
import io.proximety.hilitemall.model.offers.OfferDetailResponse
import io.proximety.hilitemall.model.paging.CoinHistoryPagingSource
import io.proximety.hilitemall.model.request.AddGuestTaste
import io.proximety.hilitemall.model.request.AddVehicleBody
import io.proximety.hilitemall.model.request.ApplyCouponBody
import io.proximety.hilitemall.model.request.ConfirmCheckoutBody
import io.proximety.hilitemall.model.request.CreateOrderBody
import io.proximety.hilitemall.model.request.EventBookBody
import io.proximety.hilitemall.model.request.EventCartConfirmBody
import io.proximety.hilitemall.model.request.EventCartConfirmItem
import io.proximety.hilitemall.model.request.FoodBookBody
import io.proximety.hilitemall.model.request.HomeBody
import io.proximety.hilitemall.model.request.LoginBody
import io.proximety.hilitemall.model.request.QuizSubmitBody
import io.proximety.hilitemall.model.request.RedeemVoucherBody
import io.proximety.hilitemall.model.request.RemoveGuestTaste
import io.proximety.hilitemall.model.request.RequestOtpBody
import io.proximety.hilitemall.model.request.ScratchCardUpdateBody
import io.proximety.hilitemall.model.request.SendOtpBody
import io.proximety.hilitemall.model.request.SpinGetRewardBody
import io.proximety.hilitemall.model.request.StoreDetailsBody
import io.proximety.hilitemall.model.request.TicketBody
import io.proximety.hilitemall.model.request.UpdateBranchBody
import io.proximety.hilitemall.model.request.UpdateCartBody
import io.proximety.hilitemall.model.request.UpdateNameBody
import io.proximety.hilitemall.model.request.UserParkingBody
import io.proximety.hilitemall.model.request.VerifyOtpBody
import io.proximety.hilitemall.model.response.AddedVehicleResponse
import io.proximety.hilitemall.model.response.BasementListResponse
import io.proximety.hilitemall.model.response.BranchesResponse
import io.proximety.hilitemall.model.response.CampaginResponse
import io.proximety.hilitemall.model.response.CoinHistoryData
import io.proximety.hilitemall.model.response.CoinHistoryResponse
import io.proximety.hilitemall.model.response.EventBookResponse
import io.proximety.hilitemall.model.response.EventCartResponse
import io.proximety.hilitemall.model.response.FAQResponse
import io.proximety.hilitemall.model.response.GenericResponse
import io.proximety.hilitemall.model.response.HomeResponse
import io.proximety.hilitemall.model.response.InstagramResponse
import io.proximety.hilitemall.model.response.JoinCampaignResponse
import io.proximety.hilitemall.model.response.LoginResponse
import io.proximety.hilitemall.model.response.NotificationClearResponse
import io.proximety.hilitemall.model.response.NotificationResponse
import io.proximety.hilitemall.model.response.ParkingZoneResponse
import io.proximety.hilitemall.model.response.ProfileUpdateResponse
import io.proximety.hilitemall.model.response.RegisterResponse
import io.proximety.hilitemall.model.response.RestaurantsResponse
import io.proximety.hilitemall.model.response.ScratchCardListResponse
import io.proximety.hilitemall.model.response.SearchResponse
import io.proximety.hilitemall.model.response.StoreCategoriesResponse
import io.proximety.hilitemall.model.response.StoreDetailsResponse
import io.proximety.hilitemall.model.response.TicketResponse
import io.proximety.hilitemall.model.response.TicketTypeResponse
import io.proximety.hilitemall.model.response.UpdateProfileResponse
import io.proximety.hilitemall.model.response.UploadedBillResponse
import io.proximety.hilitemall.model.response.booken_event_detail.BookedEventDetailResponse
import io.proximety.hilitemall.model.response.event.BookedEventsResponse
import io.proximety.hilitemall.model.response.floors.FloorResponse
import io.proximety.hilitemall.model.response.food_section.FoodItemListResponse
import io.proximety.hilitemall.model.response.food_section.add_to_cart.CartUpdateResponse
import io.proximety.hilitemall.model.response.food_section.cart_list.CartResponse
import io.proximety.hilitemall.model.response.food_section.checkout.CashCheckoutResponse
import io.proximety.hilitemall.model.response.food_section.checkout.CheckoutResponse
import io.proximety.hilitemall.model.response.food_section.checkout.OnlineCheckoutResponse
import io.proximety.hilitemall.model.response.food_section.clear_cart.ClearCartResponse
import io.proximety.hilitemall.model.response.food_section.orders.MyOrdersResponse
import io.proximety.hilitemall.model.response.food_section.payment.VerifyFoodOrderResponse
import io.proximety.hilitemall.model.response.food_section.remove_item.RemoveCartItemResponse
import io.proximety.hilitemall.model.response.food_section.search.FoodSearchResponse
import io.proximety.hilitemall.model.response.mall_services.MallServicesResponse
import io.proximety.hilitemall.model.response.offers.OffersResponse
import io.proximety.hilitemall.model.response.payment_gateway.CreateOrderResponse
import io.proximety.hilitemall.model.response.section_store.StoreSectionResponse
import io.proximety.hilitemall.model.response.store_list.StoreListResponse
import io.proximety.hilitemall.model.response.verify_email.EmailVerificationResponse
import io.proximety.hilitemall.model.response.voucher.RedeemVoucherResponse
import io.proximety.hilitemall.model.response.voucher.RedeemedVoucherResponse
import io.proximety.hilitemall.model.response.voucher.VoucherListResponse
import io.proximety.hilitemall.modules.preference.AppPreferences
import io.proximety.hilitemall.ui.activity.MainActivity
import io.proximety.hilitemall.ui.activity.MainActivity.Companion.API_TOKEN
import io.proximety.hilitemall.ui.fragment.coins.CoinHistoryFragment
import io.proximety.hilitemall.utils.longToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val service: ApiService,
    @ApplicationContext private val context: Context,
    private val preferences: AppPreferences
) {
    suspend fun parkingZones(
        basementId: String,
        branchId: String
    ): Flow<NetworkResult<ParkingZoneResponse?>> {
        return apiCall { service.parkingZones(basementId = basementId, branchId = branchId) }
    }

    suspend fun userParking(body: UserParkingBody): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.userParking(body) }
    }

    suspend fun deleteParking(id: Int): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.deleteParking(id) }
    }

    suspend fun addVehicle(body: AddVehicleBody): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.addVehicle(body) }
    }

    suspend fun editVehicle(id: Int, body: AddVehicleBody): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.editVehicle(id, body) }
    }

    suspend fun deleteVehicle(id: Int): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.deleteVehicle(id) }
    }

    suspend fun addedVehicles(): Flow<NetworkResult<AddedVehicleResponse?>> {
        return apiCall { service.addedVehicles() }
    }

    suspend fun search(keyword: String): Flow<NetworkResult<SearchResponse?>> {
        return apiCall { service.search(keyword) }
    }

    suspend fun homeRestaurants(): Flow<NetworkResult<RestaurantsResponse?>> {
        return apiCall { service.homeRestaurants() }
    }

    suspend fun basements(): Flow<NetworkResult<BasementListResponse?>> {
        return apiCall { service.basements() }
    }

    suspend fun profileUpdate(
        name: RequestBody,
        dob: RequestBody,
        email: RequestBody,
        image: MultipartBody.Part?
    ): Flow<NetworkResult<ProfileUpdateResponse?>> {
        return apiCall { service.profileUpdate(name, dob, email, image) }
    }

    suspend fun deactivate(): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.deactivate() }
    }

    suspend fun uploadedBill(billStatus: String): Flow<NetworkResult<UploadedBillResponse?>> {
        return apiCall { service.uploadedBill(billStatus) }
    }

    suspend fun ticket(body: TicketBody): Flow<NetworkResult<TicketResponse?>> {
        return apiCall { service.ticket(body) }
    }

    suspend fun ticketType(): Flow<NetworkResult<TicketTypeResponse?>> {
        return apiCall { service.ticketType() }
    }

    suspend fun faqs(): Flow<NetworkResult<FAQResponse?>> {
        return apiCall { service.faqs() }
    }

    suspend fun logout(): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.logout() }
    }

    suspend fun logoutAll(): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.logoutAll() }
    }

    suspend fun requestOtp(body: RequestOtpBody): Flow<NetworkResult<RegisterResponse?>> {
        return apiCall { service.requestOtp(body) }
    }

    suspend fun login(body: LoginBody): Flow<NetworkResult<LoginResponse?>> {
        return apiCall { service.login(body) }
    }

    suspend fun updateName(body: UpdateNameBody): Flow<NetworkResult<UpdateProfileResponse?>> {
        return apiCall { service.updateName(body) }
    }

    suspend fun branches(): Flow<NetworkResult<BranchesResponse?>> {
        return apiCall { service.branches() }
    }

    @SuppressLint("HardwareIds")
    suspend fun updateBranch(branchID: Int): Flow<NetworkResult<GenericResponse?>> {
        val deviceID = Secure.getString(context.contentResolver, Secure.ANDROID_ID)
        return apiCall { service.updateBranch(UpdateBranchBody(branchID, deviceID)) }
    }

    suspend fun home(body: HomeBody): Flow<NetworkResult<HomeResponse?>> {
        return apiCall {
            service.home(
                deviceId = body.deviceId,
                deviceName = body.deviceName,
                deviceType = body.deviceType,
                pushToken = body.pushToken
            )
        }
    }

    suspend fun storeDetails(body: StoreDetailsBody): Flow<NetworkResult<StoreDetailsResponse?>> {
        return apiCall { service.storeDetails(body) }
    }

    suspend fun notifications(): Flow<NetworkResult<NotificationResponse?>> {
        return apiCall { service.notifications() }
    }

    suspend fun notificationsClear(): Flow<NetworkResult<NotificationClearResponse?>> {
        return apiCall { service.notificationsClear() }
    }

    suspend fun storeCategories(): Flow<NetworkResult<StoreCategoriesResponse?>> {
        return apiCall { service.storeCategories() }
    }

    suspend fun storeSection(): Flow<NetworkResult<StoreSectionResponse?>> {
        return apiCall { service.storeSection() }
    }

    suspend fun floors(): Flow<NetworkResult<FloorResponse?>> {
        return apiCall { service.floors() }
    }

    suspend fun storeList(
        categoryIds: String,
        floorId: String
    ): Flow<NetworkResult<StoreListResponse?>> {
        return apiCall { service.storeList(categoryIds = categoryIds, floorId = floorId) }
    }

    suspend fun billUpload(
        billType: RequestBody,
        images: List<MultipartBody.Part>
    ): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.billUpload(billType, images) }
    }

    suspend fun offersSection(): Flow<NetworkResult<OffersResponse?>> {
        return apiCall { service.offersSection() }
    }

    suspend fun offerDetail(id: Int): Flow<NetworkResult<OfferDetailResponse?>> {
        return apiCall { service.offerDetail(id) }
    }

    suspend fun eventDetail(id: String): Flow<NetworkResult<EventDetailsResponse?>> {
        return apiCall { service.eventDetail(id) }
    }

    /*suspend fun eventCategory(eventID: String): Flow<NetworkResult<EventCategoryResponse?>> {
        return apiCall { service.eventCategory(eventID) }
    }*/

    suspend fun eventTickets(
        eventID: Int,
        eventCategoryId: Int
    ): Flow<NetworkResult<EventTicketsResponse?>> {
        return apiCall { service.eventTickets(eventID, eventCategoryId) }
    }

    suspend fun eventCartConfirm(body: List<EventCartConfirmItem>): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.eventCartConfirm(EventCartConfirmBody(body)) }
    }

    suspend fun eventCart(eventID: Int): Flow<NetworkResult<EventCartResponse?>> {
        return apiCall { service.eventCart(eventID) }
    }

    suspend fun bookedEvents(eventID: String): Flow<NetworkResult<BookedEventsResponse?>> {
        return apiCall { service.bookedEvents(eventID) }
    }

    suspend fun bookedEventDetail(ticketID: Int): Flow<NetworkResult<BookedEventDetailResponse?>> {
        return apiCall { service.bookedEventDetail(ticketID) }
    }

    suspend fun eventBookSuccess(body: EventBookBody): Flow<NetworkResult<EventBookResponse?>> {
        return apiCall { service.eventBookSuccess(body) }
    }

    suspend fun instagramFeed(url: String): Flow<NetworkResult<InstagramResponse?>> {
        return apiCall(true) { service.instagramFeed(url) }
    }

    suspend fun scratchCardUpdate(body: ScratchCardUpdateBody): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.scratchCardUpdate(body) }
    }

    suspend fun scratchCards(): Flow<NetworkResult<ScratchCardListResponse?>> {
        return apiCall { service.scratchCards() }
    }

    suspend fun sendOtp(body: SendOtpBody): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.sendOtp(body) }
    }

    suspend fun verifyOtp(body: VerifyOtpBody): Flow<NetworkResult<EmailVerificationResponse?>> {
        return apiCall { service.verifyOtp(body) }
    }

    suspend fun createOrder(body: CreateOrderBody): Flow<NetworkResult<CreateOrderResponse?>> {
        return apiCall { service.createOrder(body) }
    }

    suspend fun vouchersList(): Flow<NetworkResult<VoucherListResponse?>> {
        return apiCall { service.vouchersList() }
    }

    suspend fun redeemVoucher(body: RedeemVoucherBody): Flow<NetworkResult<RedeemVoucherResponse?>> {
        return apiCall { service.redeemVoucher(body) }
    }

    suspend fun redeemedVoucher(): Flow<NetworkResult<RedeemedVoucherResponse?>> {
        return apiCall { service.redeemedVoucher() }
    }

    suspend fun mallServices(): Flow<NetworkResult<MallServicesResponse?>> {
        return apiCall { service.mallServices() }
    }

    suspend fun restaurant(
        restaurantID: Int,
        isTakeaway: Int,
        forceClearCart: Int
    ): Flow<NetworkResult<FoodItemListResponse?>> {
        return apiCall {
            service.restaurant(
                restaurantID = restaurantID,
                isTakeaway = isTakeaway,
                forceClearCart = forceClearCart
            )
        }
    }

    suspend fun campaign(campaignID: Int): Flow<NetworkResult<CampaginResponse?>> {
        return apiCall { service.campaign(campaignID) }
    }

    suspend fun joinCampaign(data: String): Flow<NetworkResult<JoinCampaignResponse?>> {
        return apiCall { service.joinCampaign(data) }
    }

    suspend fun updateCart(body: UpdateCartBody): Flow<NetworkResult<CartUpdateResponse?>> {
        return apiCall { service.updateCart(body) }
    }

    suspend fun cart(): Flow<NetworkResult<CartResponse?>> {
        return apiCall { service.cart() }
    }

    suspend fun clearCart(): Flow<NetworkResult<ClearCartResponse?>> {
        return apiCall { service.clearCart() }
    }

    suspend fun removeCartItem(cartItemID: Int): Flow<NetworkResult<RemoveCartItemResponse?>> {
        return apiCall { service.removeCartItem(cartItemID) }
    }

    suspend fun cashCheckout(body: ConfirmCheckoutBody): Flow<NetworkResult<CashCheckoutResponse?>> {
        return apiCall { service.cashCheckout(body) }
    }

    suspend fun onlineCheckout(body: ConfirmCheckoutBody): Flow<NetworkResult<OnlineCheckoutResponse?>> {
        return apiCall { service.onlineCheckout(body) }
    }

    suspend fun searchFood(
        restaurantID: Int,
        isTakeaway: Int,
        keyword: String
    ): Flow<NetworkResult<FoodSearchResponse?>> {
        return apiCall {
            service.searchFood(
                restaurantID = restaurantID,
                isTakeaway = isTakeaway,
                keyword = keyword
            )
        }
    }

    suspend fun checkout(): Flow<NetworkResult<CheckoutResponse?>> {
        return apiCall { service.checkout() }
    }

    suspend fun myOrders(): Flow<NetworkResult<MyOrdersResponse?>> {
        return apiCall { service.myOrders() }
    }

    suspend fun verifyFoodOrder(body: FoodBookBody): Flow<NetworkResult<VerifyFoodOrderResponse?>> {
        return apiCall { service.verifyFoodOrder(body) }
    }

    suspend fun applyCoupon(body: ApplyCouponBody): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.applyCoupon(body) }
    }

    suspend fun addGuestTaste(
        cartID: Int,
        guestTaste: String
    ): Flow<NetworkResult<GenericResponse?>> {
        return apiCall {
            service.addGuestTaste(
                AddGuestTaste(
                    cart_id = cartID,
                    guest_taste = guestTaste
                )
            )
        }
    }

    suspend fun removeGuestTaste(cartID: Int): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.removeGuestTaste(RemoveGuestTaste(cart_id = cartID)) }
    }

    suspend fun orderDetails(orderID: Int): Flow<NetworkResult<SingleOrderResponse?>> {
        return apiCall { service.orderDetails(orderID) }
    }

    suspend fun reorder(orderID: Int): Flow<NetworkResult<GenericResponse?>> {
        return apiCall { service.reorder(orderID) }
    }

    suspend fun spinGetReward(
        body: SpinGetRewardBody
    ): Flow<NetworkResult<SpinGetRewardResponse?>> {
        return apiCall { service.spinGetReward(body) }
    }

    fun coinHistory(): Flow<PagingData<CoinHistoryData>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CoinHistoryPagingSource(service) }
        ).flow
    }

    suspend fun userQuizzes(
        body: QuizSubmitBody
    ): Flow<NetworkResult<UserQuizzesResponse?>> {
        return apiCall { service.userQuizzes(body) }
    }

    // Generalized function to handle API calls
    private suspend fun <T> apiCall(
        ignore401: Boolean = false,
        apiCall: suspend () -> Response<T>
    ): Flow<NetworkResult<T?>> {
//        val callTime = System.currentTimeMillis()
//        val callingFunction = Thread.currentThread().stackTrace.getOrNull(3)?.methodName
//        Timber.tag("Repository").d("[$callingFunction]: API call initiated @ $callTime")
        return flow {
            emit(NetworkResult.Loading)
            val response = apiCall()

            val result = when {
                response.isSuccessful -> {
                    NetworkResult.Success(response.body())
                }

                !ignore401 && response.code() == 401 -> {
                    forceLogout()
                    NetworkResult.Failure(
                        code = response.code(),
                        throwable = HttpException(response),
                        message = "Session expired"
                    )
                }

                else -> {
                    val msg = response.errorBody()?.string()?.let {
                        JSONObject(it)
                    }?.getString("message")

                    NetworkResult.Failure(
                        code = response.code(),
                        throwable = HttpException(response),
                        message = msg ?: "Something went wrong"// response.message()
                    )
                }
            }

            emit(result)
//            val doneTime = System.currentTimeMillis()
//            Timber.tag("Repository").d("[$callingFunction]: API call completed in ${doneTime - callTime}ms  (done: $doneTime ;  call: $callTime)")
        }.catch { throwable ->
            emit(handleNetworkExceptions(throwable, ignore401))
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun forceLogout() {
        withContext(Dispatchers.Main) {
            API_TOKEN = ""
            preferences.clear()
            preferences.openTo = OnboardFlow.LOGIN
            context.longToast("You have been logged out!\nPlease login again.")
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(this)
            }
        }
    }

    // Centralized error handling for network exceptions
    private suspend fun handleNetworkExceptions(
        throwable: Throwable,
        ignore401: Boolean
    ): NetworkResult.Failure {
        Timber.tag("Repository").e(throwable, "Network error/exception")

        // Check if the error is an HTTP exception with a 401 status code
        if (!ignore401 && throwable is HttpException && throwable.code() == 401) {
            forceLogout()
        }

        return when (throwable) {
            is UnknownHostException, is SocketTimeoutException, is NoRouteToHostException, is ConnectException, is IOException -> {
                NetworkResult.Failure(
                    code = 503,
                    throwable = throwable,
                    message = "No internet connection"
                )
            }

            is HttpException -> {
                NetworkResult.Failure(
                    code = throwable.code(),
                    throwable = throwable,
                    message = throwable.response()?.message() ?: "HTTP error"
                )
            }

            else -> {
                NetworkResult.Failure(
                    code = 500,
                    throwable = throwable,
                    message = throwable.message ?: "Unknown error occurred."
                )
            }
        }
    }
}