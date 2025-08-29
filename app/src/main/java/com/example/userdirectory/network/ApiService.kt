package io.proximety.hilitemall.network

import io.proximety.hilitemall.model.SingleOrderResponse
import io.proximety.hilitemall.model.SpinGetRewardResponse
import io.proximety.hilitemall.model.UserQuizzesResponse
import io.proximety.hilitemall.model.events.EventDetailsResponse
import io.proximety.hilitemall.model.events.EventTicketsResponse
import io.proximety.hilitemall.model.offers.OfferDetailResponse
import io.proximety.hilitemall.model.request.AddGuestTaste
import io.proximety.hilitemall.model.request.AddVehicleBody
import io.proximety.hilitemall.model.request.ApplyCouponBody
import io.proximety.hilitemall.model.request.ConfirmCheckoutBody
import io.proximety.hilitemall.model.request.CreateOrderBody
import io.proximety.hilitemall.model.request.EventBookBody
import io.proximety.hilitemall.model.request.EventCartConfirmBody
import io.proximety.hilitemall.model.request.FoodBookBody
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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @POST("user-vehicles")
    suspend fun addVehicle(@Body body: AddVehicleBody): Response<GenericResponse>

    @PUT("user-vehicles/{id}")
    suspend fun editVehicle(
        @Path("id") id: Int,
        @Body body: AddVehicleBody
    ): Response<GenericResponse>

    @DELETE("user-vehicles/{id}")
    suspend fun deleteVehicle(@Path("id") id: Int): Response<GenericResponse>

    @GET("user-vehicles")
    suspend fun addedVehicles(): Response<AddedVehicleResponse>

    @GET("searches")
    suspend fun search(@Query("keyword") keyword: String): Response<SearchResponse>

    @GET("home-restaurants")
    suspend fun homeRestaurants(): Response<RestaurantsResponse>

    @GET("basements")
    suspend fun basements(): Response<BasementListResponse>

    @Multipart
    @POST("profile-update")
    suspend fun profileUpdate(
        @Part("name") name: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("email") email: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<ProfileUpdateResponse>

    @POST("user-deactivate")
    suspend fun deactivate(): Response<GenericResponse>

    @GET("bills")
    suspend fun uploadedBill(@Query("bill_status") billStatus: String): Response<UploadedBillResponse>

    @POST("support-tickets")
    suspend fun ticket(@Body body: TicketBody): Response<TicketResponse>

    @GET("get-ticket-types")
    suspend fun ticketType(): Response<TicketTypeResponse>

    @GET("faqs")
    suspend fun faqs(): Response<FAQResponse>

    @POST("logout")
    suspend fun logout(): Response<GenericResponse>

    @POST("logout-all")
    suspend fun logoutAll(): Response<GenericResponse>

    @POST("register")
    suspend fun requestOtp(@Body body: RequestOtpBody): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body body: LoginBody): Response<LoginResponse>

    @POST("update-name")
    suspend fun updateName(@Body body: UpdateNameBody): Response<UpdateProfileResponse>

    @GET("branches")
    suspend fun branches(): Response<BranchesResponse>

    @POST("update-user-branch")
    suspend fun updateBranch(@Body body: UpdateBranchBody): Response<GenericResponse>

    /*@POST("home")
    suspend fun home(@Body body: HomeBody): Response<HomeResponse>*/

    @GET("home")
    suspend fun home(
        @Query("deviceId") deviceId: String,
        @Query("deviceName") deviceName: String,
        @Query("deviceType") deviceType: String,
        @Query("pushToken") pushToken: String,
    ): Response<HomeResponse>

    @POST("store-view")
    suspend fun storeDetails(@Body body: StoreDetailsBody): Response<StoreDetailsResponse>

    @GET("notifications")
    suspend fun notifications(): Response<NotificationResponse>

    @POST("notifications")
    suspend fun notificationsClear(): Response<NotificationClearResponse>

    @GET("categories")
    suspend fun storeCategories(): Response<StoreCategoriesResponse>

    @GET("home-stores")
    suspend fun storeSection(): Response<StoreSectionResponse>

    @GET("floors")
    suspend fun floors(): Response<FloorResponse>

    @GET("stores")
    suspend fun storeList(
        @Query("category_ids") categoryIds: String,
        @Query("floor_id") floorId: String
    ): Response<StoreListResponse>

    @Multipart
    @POST("bills")
    suspend fun billUpload(
        @Part("bill_type") billType: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): Response<GenericResponse>

    @GET("offers")
    suspend fun offersSection(): Response<OffersResponse>

    @GET("parking-zones")
    suspend fun parkingZones(
        @Query("basementId") basementId: String,
        @Query("branchId") branchId: String
    ): Response<ParkingZoneResponse>

    @POST("user-parking")
    suspend fun userParking(@Body body: UserParkingBody): Response<GenericResponse>

    @DELETE("user-parking/{id}")
    suspend fun deleteParking(@Path("id") id: Int): Response<GenericResponse>

    @GET("offers/{id}")
    suspend fun offerDetail(@Path("id") id: Int): Response<OfferDetailResponse>

    @GET("events/{id}")
    suspend fun eventDetail(@Path("id") id: String): Response<EventDetailsResponse>

    /*  @GET("event-ticket-categories")
      suspend fun eventCategory(@Query("eventId") eventId: String): Response<EventCategoryResponse>*/

    @GET("event-tickets")
    suspend fun eventTickets(
        @Query("eventId") eventId: Int,
        @Query("eventCategoryId") eventCategoryId: Int
    ): Response<EventTicketsResponse>

    @POST("event-ticket-carts")
    suspend fun eventCartConfirm(@Body body: EventCartConfirmBody): Response<GenericResponse>

    @GET("event-ticket-carts")
    suspend fun eventCart(@Query("eventId") eventId: Int): Response<EventCartResponse>

    @GET("booked-event-tickets")
    suspend fun bookedEvents(@Query("eventId") eventId: String): Response<BookedEventsResponse>

    @GET("booked-event-tickets/{id}")
    suspend fun bookedEventDetail(@Path("id") ticketID: Int): Response<BookedEventDetailResponse>

    @POST("booked-event-tickets/verify-order")
    suspend fun eventBookSuccess(@Body body: EventBookBody): Response<EventBookResponse>

    @GET
    suspend fun instagramFeed(@Url urlString: String): Response<InstagramResponse>

    @POST("scratch-card/update")
    suspend fun scratchCardUpdate(@Body body: ScratchCardUpdateBody): Response<GenericResponse>

    @GET("scratch-cards")
    suspend fun scratchCards(): Response<ScratchCardListResponse>

    @POST("send-otp")
    suspend fun sendOtp(@Body body: SendOtpBody): Response<GenericResponse>

    @POST("verify-otp")
    suspend fun verifyOtp(@Body body: VerifyOtpBody): Response<EmailVerificationResponse>

    @POST("booked-event-tickets/create-order")
    suspend fun createOrder(@Body body: CreateOrderBody): Response<CreateOrderResponse>

    @GET("vouchers-list")
    suspend fun vouchersList(): Response<VoucherListResponse>

    @POST("redeem-voucher")
    suspend fun redeemVoucher(@Body body: RedeemVoucherBody): Response<RedeemVoucherResponse>

    @GET("redeemed-voucher-list")
    suspend fun redeemedVoucher(): Response<RedeemedVoucherResponse>

    @GET("mall-services")
    suspend fun mallServices(): Response<MallServicesResponse>

    @GET("home-restaurants/{id}")
    suspend fun restaurant(
        @Path("id") restaurantID: Int,
        @Query("is_takeaway") isTakeaway: Int,
        @Query("clear_cart") forceClearCart: Int
    ): Response<FoodItemListResponse>

    @POST("carts")
    suspend fun updateCart(@Body body: UpdateCartBody): Response<CartUpdateResponse>

    @GET("carts")
    suspend fun cart(): Response<CartResponse>

    @GET("carts/checkout")
    suspend fun checkout(): Response<CheckoutResponse>

    @GET("orders")
    suspend fun myOrders(): Response<MyOrdersResponse>

    @POST("orders/verify-order")
    suspend fun verifyFoodOrder(@Body body: FoodBookBody): Response<VerifyFoodOrderResponse>

    @POST("orders")
    suspend fun cashCheckout(@Body body: ConfirmCheckoutBody): Response<CashCheckoutResponse>

    @GET("restaurants/{id}/search")
    suspend fun searchFood(
        @Path("id") restaurantID: Int,
        @Query("is_takeaway") isTakeaway: Int,
        @Query("keyword") keyword: String
    ): Response<FoodSearchResponse>

    @POST("orders")
    suspend fun onlineCheckout(@Body body: ConfirmCheckoutBody): Response<OnlineCheckoutResponse>

    @DELETE("clear-cart")
    suspend fun clearCart(): Response<ClearCartResponse>

    @DELETE("remove-from-cart/{id}")
    suspend fun removeCartItem(
        @Path("id") cartItemID: Int
    ): Response<RemoveCartItemResponse>

    @GET("app-campaigns/{id}")
    suspend fun campaign(@Path("id") campaignID: Int): Response<CampaginResponse>

    @GET("join-campaign")
    suspend fun joinCampaign(@Query("data") data: String): Response<JoinCampaignResponse>

    @POST("carts/apply-coupons")
    suspend fun applyCoupon(
        @Body body: ApplyCouponBody
    ): Response<GenericResponse>

    @POST("carts/add-guest-tastes")
    suspend fun addGuestTaste(
        @Body body: AddGuestTaste
    ): Response<GenericResponse>

    @POST("carts/add-guest-tastes")
    suspend fun removeGuestTaste(
        @Body body: RemoveGuestTaste
    ): Response<GenericResponse>

    @GET("orders/{order_id}")
    suspend fun orderDetails(@Path("order_id") orderID: Int): Response<SingleOrderResponse>

    @POST("orders/re-order/{order_id}")
    suspend fun reorder(@Path("order_id") orderID: Int): Response<GenericResponse>

    @POST("spin-get-reward")
    suspend fun spinGetReward(
        @Body body: SpinGetRewardBody
    ): Response<SpinGetRewardResponse>

    @GET("get-user-points")
    suspend fun coinHistory(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<CoinHistoryResponse>

    @POST("user-quizzes")
    suspend fun userQuizzes(@Body body: QuizSubmitBody): Response<UserQuizzesResponse>
}