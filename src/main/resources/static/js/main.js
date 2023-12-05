(function ($) {
	"use strict";

	// Spinner
	var spinner = function () {
		setTimeout(function () {
			if ($('#spinner').length > 0) {
				$('#spinner').removeClass('show');
			}
		}, 1);
	};
	spinner();


	// Initiate the wowjs
	new WOW().init();


	// Fixed Navbar
	$('.fixed-top').css('top', $('.top-bar').height());
	$(window).scroll(function () {
		if ($(this).scrollTop()) {
			$('.fixed-top').addClass('bg-dark').css('top', 0);
		} else {
			$('.fixed-top').removeClass('bg-dark').css('top', $('.top-bar').height());
		}
	});


	// Back to top button
	$(window).scroll(function () {
		if ($(this).scrollTop() > 300) {
			$('.back-to-top').fadeIn('slow');
		} else {
			$('.back-to-top').fadeOut('slow');
		}
	});
	$('.back-to-top').click(function () {
		$('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
		return false;
	});


	// Header carousel
	$(".header-carousel").owlCarousel({
		autoplay: false,
		smartSpeed: 1500,
		loop: true,
		nav: true,
		dots: false,
		items: 1,
		navText: [
			'<i class="bi bi-chevron-left"></i>',
			'<i class="bi bi-chevron-right"></i>'
		]
	});


	// Facts counter
	$('[data-toggle="counter-up"]').counterUp({
		delay: 10,
		time: 2000
	});


	// Testimonials carousel
	$(".testimonial-carousel").owlCarousel({
		autoplay: false,
		smartSpeed: 1000,
		margin: 25,
		loop: true,
		center: true,
		dots: false,
		nav: true,
		navText: [
			'<i class="bi bi-chevron-left"></i>',
			'<i class="bi bi-chevron-right"></i>'
		],
		responsive: {
			0: {
				items: 1
			},
			768: {
				items: 2
			},
			992: {
				items: 3
			}
		}
	});


})(jQuery);
var app = angular.module('app', ['ngRoute']);

let host = "http://localhost:8080/rest";

app.filter('vndCurrency', function () {
	return function (input) {
		if (!input) return '';
		return input.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
	};
});
app.filter('dateFilter', function ($filter) {
	return function (input) {
		if (input) {
			var date = new Date(input);
			var formattedDate = $filter('date')(date, 'dd-MM-yyyy');
			var formattedTime = $filter('date')(date, 'HH:mm');
			return formattedTime + ' ' + formattedDate;
		}
		return '';
	};
});

app.filter('dateTimeLocalFilter', function ($filter) {
	return function (input) {
		if (input) {
			var date = new Date(input);
			return $filter('date')(date, 'yyyy-MM-ddTHH:mm:ss');
		}
		return '';
	};
});
app.filter('toTimeStamp', function () {
	return function (dateTimeLocalString) {
		var date = new Date(dateTimeLocalString);
		var timestamp = date.getTime();
		return timestamp;
	};
});
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
app.controller('ProductController', function ($scope, $http, $filter, $location, $routeParams) {
	// product
	$scope.listAllProducts = [];
	$scope.filteredProducts = [];
	$scope.filterByCategory = [];
	$scope.filteredAndPagingProduct = [];

	$scope.totalPage = 0;
	$scope.currentPage = 1;
	$scope.itemsPerPage = 0;
	$scope.category = 0;

	$scope.selectedProductId = 0;
	// category
	$scope.searchCategory = 0;
	$scope.searchKey = "";
	$scope.searchSort = "asc";

	//cart
	$scope.cart = [];
	$scope.cartLength = 0;

	$scope.imageClick = "";
	$scope.productImages = [];

	$scope.reviewList = [];
	$scope.formReview = {};
	$scope.rating = 0;
	$scope.stars = new Array(5);
	$scope.isHovered = new Array(5).fill(false);

	$scope.userInfo = {};


	$scope.categoryList = [];
	function loadAllProducts() {
		var url = `${host}/product`;
		return $http.get(url).then(response => {
			$scope.listAllProducts = response.data;
			$scope.itemsPerPage = 4;
			$scope.totalPage = Math.ceil(response.data.length / $scope.itemsPerPage);
		});
	}
	function loadCategory() {
		return $http.get(`${host}/category`).then(function (resp) {
			$scope.category = resp.data;
		});
	}
	$scope.loadPage = function (currentPage) {
		$scope.filteredProducts = $scope.listAllProducts;
		if ($scope.searchCategory != 0) {
			$scope.filterByCategory($scope.searchCategory);
		}
		if ($scope.searchKey != "") {
			$scope.filterByKey($scope.searchKey);
		}
		$scope.currentPage = currentPage;
		var begin = (currentPage - 1) * $scope.itemsPerPage;
		$scope.filteredAndPagingProduct = $scope.filteredProducts.slice(begin, begin + $scope.itemsPerPage);
		$scope.totalPage = Math.ceil($scope.filteredProducts.length / $scope.itemsPerPage);
	};

	$scope.filterByKey = function (key) {
		var filteredProducts = $scope.listAllProducts.filter(function (product) {
			return product.product.productName.toLowerCase().includes(key.toLowerCase());
		});

		$scope.totalPage = Math.ceil(filteredProducts.length / $scope.itemsPerPage);

		if (filteredProducts.length > 0) {
			$scope.filteredProducts = filteredProducts;
		}
	};



	$scope.filterByCategory = function (categoryId) {
		$scope.currentPage = 1;
		$scope.filteredProducts = $filter('filter')($scope.listAllProducts, function (product) {
			return product.product.category.categoryId === categoryId;
		});
		$scope.totalPage = Math.ceil($scope.filteredProducts.length / $scope.itemsPerPage);
	};
	$scope.setSearchCategory = function (categoryId) {
		$scope.searchCategory = categoryId;
		$scope.loadPage(1);
	}
	$scope.setSearchKey = function (key) {
		$scope.searchKey = key;
		$scope.loadPage(1);
	}
	$scope.reset = function () {
		$scope.searchCategory = 0;
		$scope.searchKey = "";
		$scope.loadPage(1);
	}



	// pagination and filters - pagination and filters - pagination and filters - pagination and filters - pagination and filters
	$scope.disablePrev = function () {
		return $scope.currentPage === 1;
	};

	$scope.disableNext = function () {
		return $scope.currentPage === $scope.totalPage;
	};
	// product images - product images - product images - product images - product images - product images - product images
	$scope.loadAllProductImages = function () {
		$scope.getprdId().then(function (productId) {
			$scope.selectedProductId = productId;
			var url = `${host}/pi/`;
			url += productId;
			$http.get(url).then(resp => {
				$scope.productImages = resp.data;
				console.log(resp.data)
			})
			$scope.loadReviews();
		})
	}
	$scope.setImgData = function (data) {
		var src = data.target.src;
		$scope.imageClick = src;
	}
	// product images - product images - product images - product images - product images - product images - product images 

	// review - review - review - review - review - review - review - review - review - review - review - review - review

	$scope.loadReviews = function () {
		var pathUrl = $location.absUrl().substring(21);
		if (pathUrl.startsWith('/product/detail/')) {
			var productId = pathUrl.split('/').pop();
			$http.get(`${host}/review/${productId}`).then(resp => {
				$scope.reviewList = resp.data;
			});
		}
	};


	function getUser() {
		var url = `${host}/account/getAuth`;
		$http.get(url).then(resp => {
			$scope.userInfo.accountId = resp.data.accountId;
			$scope.userInfo.username = resp.data.username;
			$scope.userInfo.password = resp.data.password;
			$scope.userInfo.email = resp.data.email;
			$scope.userInfo.fullName = resp.data.fullName;
			$scope.userInfo.address = resp.data.address;
			$scope.userInfo.addressDetail = resp.data.addressDetail;
			$scope.userInfo.phoneNumber = resp.data.phoneNumber;
			$scope.userInfo.photo = resp.data.photo;
			$scope.userInfo.role = resp.data.role;
		})
	}
	$scope.postReview = function () {
		$scope.formReview.rating = $scope.rating;
		const url = `${host}/review/${$scope.selectedProductId}`;
		$http.post(url, $scope.formReview).then(function (response) {
			$scope.reviewList += response.data;
			$scope.loadReviews();
		});

	}

	$scope.setHovered = function (index, hovered) {
		for (var i = 0; i <= index; i++) {
			$scope.isHovered[i] = hovered;
		}
	};
	$scope.setRating = function (rating) {
		$scope.rating = rating;
	};

	$scope.setSelectedProductId = function () {
		var fullUrl = $location.absUrl();
		var lastSegment = fullUrl.substring(fullUrl.lastIndexOf('/') + 1);
		$scope.selectedProductId = lastSegment
	}
	// review - review - review - review - review - review - review - review - review - review - review - review - review
	// cart - cart - cart - cart - cart - cart - cart - cart - cart - cart - cart - cart - cart - cart
	function updateCartLocalStorage() {
		localStorage.setItem('cart', JSON.stringify($scope.cart));
	}

	$scope.loadAllCart = function () {
		$http.get(`${host}/cart`).then(function (resp) {
			$scope.cart = resp.data;
			$scope.cartLength = resp.data.length;
			updateCartLocalStorage();
			// console.log($scope.cart);
		});
	}

	$scope.modifyCart = function (id, method) {
		// console.log(`${host}/cart/${method}/${id}`);
		$http.get(`${host}/cart/${method}/${id}`).then(function () {
			$scope.loadAllCart();
		});
	};

	$scope.deleteCart = function (id) {
		$http.get(`${host}/cart/delete/${id}`).then(function () {
			$scope.loadAllCart();
		});
	};

	$scope.clearCart = function () {
		$http.get(`${host}/cart/clear`).then(function () {
			$scope.loadAllCart();
		});
	};

	$scope.getAmountCart = function () {
		return $scope.cart.reduce(function (total, cart) {
			return total + cart.product.price * cart.quantity;
		}, 0);
	};

	function initCartFromLocalStorage() {
		$scope.cart = JSON.parse(localStorage.getItem('cart')) || [];
	}
	// cart - cart - cart - cart - cart - cart - cart - cart - cart - cart - cart - cart - cart - cart


	// Khởi tạo dữ liệu ban đầu
	loadAllProducts().then(function () {
		$scope.loadPage(1);
	});
	loadCategory();

	$scope.loadAllCart();
	// initCartFromLocalStorage();

	getUser();
	$scope.setSelectedProductId();
	$scope.loadReviews();
});

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
app.controller('OrderController', function ($scope, $http) {
	var couponCodeCheckInput = document.getElementById("couponCodeCheckInput");
	var couponCodeInput = document.getElementById("couponCodeInput");
	var urlCheck = `${host}/coupon/check`;
	$scope.submitForm = function () {
		event.preventDefault();
		$scope.checkCouponCode();
	};
	$scope.checkCouponCode = function () {
		$http.get(urlCheck + couponCodeCheckInput.value).then(resp => {
			if (resp.data.couponCode) {
				couponCodeInput.value = resp.data.couponCode;
				console.log("oke roai")
			} else {
				console.log("ngu")
			}
		});

	}
});
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
app.controller('UserController', function ($scope, $http) {
	$scope.userInfo = {};
	$scope.form = {};

	$scope.cities = [];
	$scope.districts = [];
	$scope.wards = [];

	$scope.city = null;
	$scope.district = null;
	$scope.ward = null;


	const url = `${host}/address/`;
	const urlUser = `${host}/account`;

	function getUser() {
		var url = urlUser + '/getAuth';
		$http.get(url).then(resp => {
			if (resp.data != null) {
				$scope.userInfo = resp.data;
				// $scope.form.accountId = $scope.userInfo.accountId;
				// $scope.form.username = $scope.userInfo.username;
				// $scope.form.password = $scope.userInfo.password;
				// $scope.form.email = $scope.userInfo.email;
				// $scope.form.fullName = resp.data.fullName;
				// $scope.form.address = $scope.userInfo.address;
				// $scope.form.addressDetail = $scope.userInfo.addressDetail;
				// $scope.form.phoneNumber = $scope.userInfo.phoneNumber;
				// $scope.form.photo = $scope.userInfo.photo;
				// $scope.form.admin = $scope.userInfo.admin;
				$scope.form = resp.data;
			}
		})
	}
	$scope.putUser = function () {
		$http.put(urlUser + "/" + $scope.form.username, $scope.form)
			.then(function (resp) {
				$scope.form = resp.data;
			});
	}
	$scope.loadCities = function () {
		$http.get(url + "cities").then(response => {
			$scope.cities = response.data;
		});
	};

	$scope.loadDistricts = function () {
		if ($scope.city !== null && $scope.city === '') {
			$scope.address = $scope.city.name;
			$scope.districts = [];
			$scope.wards = [];
			return;
		}

		$http.get(url + $scope.city.code + "/districts").then(function (response) {
			$scope.districts = response.data;
			$scope.district = null;
			$scope.wards = [];
			$scope.setAddress();
		});

	};

	$scope.loadWards = function () {
		if (!$scope.district) {
			$scope.wards = [];
			return;
		}
		$http.get(url + $scope.district.code + "/wards").then(function (response) {
			$scope.wards = response.data;
			$scope.setAddress();
		});
	};

	$scope.setAddress = function () {
		// $scope.form.address = '';
		// if($scope.city != null) {
		// 	$scope.form.address = $scope.city.name_with_type;
		// 	if($scope.district != null) {
		// 		$scope.form.address = $scope.district.path_with_type;
		// 		if($scope.ward != null) {
		// 			$scope.form.address = $scope.ward.path_with_type;
		// 		}
		// 	}
		// }

		if ($scope.ward != null) {
			$scope.form.address = '';
			$scope.form.address = $scope.ward.path_with_type;
		}
	}





	getUser();
	$scope.loadCities();
});
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
app.controller('AddressController', function ($scope, $http) {
	$scope.form = {};

	$scope.cities = [];
	$scope.districts = [];
	$scope.wards = [];

	$scope.city = null;
	$scope.district = null;
	$scope.ward = null;


	const url = `${host}/address/`;

	$scope.loadCities = function () {
		$http.get(url + "cities").then(response => {
			$scope.cities = response.data;
			console.log(response.data)
		});
	};

	$scope.loadDistricts = function () {
		if ($scope.city !== null && $scope.city === '') {
			$scope.address = $scope.city.name;
			$scope.districts = [];
			$scope.wards = [];
			return;
		}

		$http.get(url + $scope.city.code + "/districts").then(function (response) {
			$scope.districts = response.data;
			$scope.district = null;
			$scope.wards = [];
			$scope.setAddress();
		});

	};

	$scope.loadWards = function () {
		if (!$scope.district) {
			$scope.wards = [];
			return;
		}
		$http.get(url + $scope.district.code + "/wards").then(function (response) {
			$scope.wards = response.data;
			$scope.setAddress();
		});
	};

	$scope.setAddress = function () {
		if ($scope.ward != null) {
			$scope.form.address = '';
			$scope.form.address = $scope.ward.path_with_type;
		}
	}

	$scope.loadCities();
});
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
app.controller('AdminProductController', function ($scope, $http) {
	$scope.products = [];
	$scope.category = [];
	$scope.form = {};

	$scope.loadAll = function () {
		const url = `${host}/product`;
		$http.get(url).then(resp => {
			$scope.products = resp.data;
		});
	}

	$scope.loadCategory = function () {
		const url = `${host}/category`;
		$http.get(url).then(resp => {
			$scope.category = resp.data;
		});
	}

	$scope.reset = function () {
		var product = {};
		product.price = 0;
		$scope.form = product;
	}

	$scope.save = function (method) {
		var url = `${host}/product`;
		if (method === "Add") {
			$http.post(url, $scope.form).then(() => {
				$scope.loadAll();
				$scope.reset();
			});
		}
		if (method === "Update") {
			$http.put(url, $scope.form).then(() => {
				$scope.loadAll();
				$scope.reset();
			});
		}

	}

	$scope.delete = function (productId) {
		const url = `${host}/product/${productId}`;
		$http.delete(url).then(() => {
			$scope.loadAll();
			$scope.reset();
		});
	}

	$scope.loadForm = function (product) {
		if (!product) {
			$scope.form.price = parseFloat($scope.form.price) || 0;
			$scope.scrollToForm();
		}
		if (product) {
			$scope.form = angular.copy(product);
			$scope.form.price = parseFloat($scope.form.price) || 0;
			$scope.scrollToForm();
		}
	};
	$scope.scrollToForm = function () {
		var formElement = document.getElementById('formProduct');
		if (formElement) {
			formElement.scrollIntoView({
				behavior: 'smooth',
				block: 'start',
				inline: 'nearest'
			});
		}
	};
	$scope.loadAll();

	$scope.loadCategory();
	$scope.reset();
});

// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
app.controller('AdminOrderController', function ($scope, $http, $window) {
	$scope.orders = [];
	$scope.orderitems = [];
	$scope.filterOptions = 'All';
	const url = `${host}/order`;

	$scope.loadAll = function () {
		const url = `${host}/order`;
		$http.get(url).then(resp => {
			$scope.orders = resp.data;
		});
	}
	$scope.loadOrderStatus = function () {
		const url = `${host}/order/OrderStatus`;
		$http.get(url).then(resp => {
			$scope.orderStatusOptions = resp.data;
		});
	}
	$scope.getOrderItems = function (order) {
		$scope.orderitems = [];
		$scope.orderitems = order.orderItems;
	}


	$scope.getAmount = function (orderItems) {
		return orderItems.reduce(function (total, product) {
			return total + product.product.price * product.quantity;
		}, 0);
	}
	$scope.message = '';
	$scope.showDeleteButton = false;
	$scope.setOrderToDelete = function (order) {
		$scope.orderToDelete = order;
		$scope.message = 'Bạn có chắc muốn xóa đơn hàng này?';
		$scope.showDeleteButton = true;
	};
	$scope.confirmDelete = function () {
		if ($scope.orderToDelete) {
			var urlDelete = url + '/' + $scope.orderToDelete.orderId;
			$http.delete(urlDelete).then(function (resp) {
				$scope.loadAll();
				$scope.message = 'Đơn hàng đã được xóa thành công.';
				$scope.showDeleteButton = false; // Ẩn nút xóa khi xóa thành công
			});
			// Xóa biến orderToDelete
			$scope.orderToDelete = null;
		}
	}

	$scope.updateStatus = function (orderId, newStatus) {
		var url = `${host}/order/${orderId}`;
		$scope.form = { orderId: orderId, status: newStatus };

		$http.put(url, $scope.form).then(
			function (response) {
				// Xử lý phản hồi từ server nếu cần
				console.log(response.data);

				// Cập nhật message và hiển thị modal sau khi cập nhật thành công
				$scope.message = 'Hóa đơn "' + orderId + '" được cập nhật sang trạng thái "' + newStatus + '"';
				$('#deleteConfirmationModal').modal('show');

				// Nếu cần, bạn có thể gọi $scope.loadAll() ở đây hoặc ở bất cứ nơi nào phù hợp
			},
			function (error) {
				// Xử lý lỗi nếu cần
				console.error('Error updating order status:', error);
			}
		);
	};



	$scope.applyFilter = function (statusOption) {
		$scope.filterByStatus = (statusOption !== 'All');
		$scope.filterOptions = statusOption;
	};
	$scope.loadOrderStatus();
	$scope.loadAll();
});

app.controller('TopBarController', function ($interval, $scope) {
	$scope.date = new Date();

	$interval(function () {
		$scope.date = new Date();
	}, 1000);
})

app.controller('CouponController', function ($http, $scope, $filter) {
	var url = `${host}/coupon`;

	$scope.coupons = [];
	$scope.form = {};

	$scope.loadAll = function () {
		$http.get(url).then(resp => {
			$scope.coupons = resp.data;
		});
	}
	$scope.add = function () {
		$scope.form.couponId = 0;
		$http.post(url, $scope.form).then(() => $scope.loadAll());
	}
	$scope.update = function () {
		$scope.form.startDate = $filter('date')($scope.form.startDate, 'HH:mm:ss yyyy-MM-dd');
		$scope.form.endDate = $filter('date')($scope.form.endDate, 'HH:mm:ss yyyy-MM-dd');
		console.log($scope.form)
		$http.put(url, $scope.form).then(() => $scope.loadAll());
	}

	$scope.delete = function (id) {
		$http.delete(url + '/' + id).then(() => $scope.loadAll()).then(() => $scope.reset());
	}
	$scope.loadForm = function (coupon) {
		coupon.startDate = new Date(moment(coupon.startDate, 'HH:mm:ss DD-MM-YYYY'));
		coupon.endDate = new Date(moment(coupon.endDate, 'HH:mm:ss DD-MM-YYYY'));
		$scope.form = angular.copy(coupon);
	};

	$scope.reset = function () {
		$scope.form = {};
	}
	//khởi tạo dữ liệu ban đầu
	$scope.loadAll();
});
app.controller('AdminAccountController', function ($scope, $http) {
	$scope.accounts = [];
	$scope.form = {};
	$scope.temporarySearchText = '';
	$scope.modalMessage = '';

	$scope.loadAll = function () {
		const url = `${host}/account`;
		$http.get(url).then(resp => {
			$scope.accounts = resp.data;
		});
	}
	$scope.getRoleStyle = function (role) {
		switch (role) {
			case 'USER':
				return { 'color': 'black' };
			case 'MANAGER':
				return { 'color': 'blue' };
			case 'ADMIN':
				return { 'color': 'red' };
			case 'SUPERADMIN':
				return { 'color': 'green' };
			default:
				return {};
		}
	},
		$scope.search = function () {
			$scope.searchText = $scope.temporarySearchText;
		};
	$scope.showReasonBlockerModal = function (reason) {
		$scope.modalMessage = reason;
		$('#messageModal').modal('show');
	};

	$scope.filterOptions = 'all';
	$scope.filterCondition = function (account) {
		if ($scope.filterOptions === 'activity') {
			return !account.account.banned;
		} else if ($scope.filterOptions === 'blocker') {
			return account.account.banned;
		} else {
			return true;
		}
	};
	$scope.filterByRole = function () {
		if ($scope.form.role === 'RoleAll') {
			$scope.roleFilter = $scope.filterAllRoles;
		} else {
			var roleFilter = function (account) {
				return account.account.role === $scope.form.role;
			};
			$scope.roleFilter = roleFilter;
		}
	};

	$scope.editAccount = function (account) {
		$scope.form = angular.copy(account);
		$scope.form.condition = $scope.form.banned ? 'blocker' : 'activity';
		//   console.log($scope.form);
	};

	$scope.reset = function () {
		$scope.form = {
			username: '',
			email: '',
			address: '',
			phoneNumber: '',
			condition: 'activity',
			reasonBlocker: '',
			role: 'USER'
		};
	};

	$scope.update = function () {
		var formAccount = $scope.form;
		formAccount.banned = formAccount.condition === 'activity' ? false : true;
		const url = `${host}/account/${formAccount.username}`;
		if (formAccount != null) {
			$http.put(url, formAccount).then(() => {
				$scope.loadAll();
				$scope.showModal('Lưu tài khoản thành công!');
			}).catch(function (error) {
				$scope.showModal('Lỗi khi lưu tài khoản: ' + error.message);
			});
		}
	}

	$scope.delete = function (username) {
		const url = `${host}/account/${username}`;
		$http.delete(url).then(function () {
			$scope.loadAll();
			$scope.reset();
			$scope.showModal('Xóa tài khoản thành công!');
		}).catch(function (error) {
			$scope.showModal('Lỗi khi xóa tài khoản: ' + error.message);
		});
	}

	$scope.handleFileSelect = function (element) {
		var fileName = element.files[0].name;
		console.log('Selected file name:', fileName);
		$scope.form.photo = fileName;
	};

	$scope.getRelativeImagePath = function (imageName) {
		return "/images/accountPhoto/" + imageName;
	};

	$scope.showModal = function (message) {
		$scope.modalMessage = message;
		$('#messageModal').modal('show');
	};

	$scope.hideModal = function () {
		$('#messageModal').modal('hide');
	};


	$scope.reset();
	$scope.loadAll();
});

app.controller('RegisterController', function ($http, $scope) {
	var url = `${host}/account/randomname`;
	$scope.randomName = {};

	$scope.formRegister = {};

	$scope.cities = [];
	$scope.districts = [];
	$scope.wards = [];

	$scope.city = null;
	$scope.district = null;
	$scope.ward = null;


	const urlAddress = `${host}/address/`;
	const urlName = `${host}/account/randomname`;


	$scope.get = function () {
		$http.get(urlName).then(resp => {
			$scope.randomName = resp.data;
		});
	}

	$scope.loadCities = function () {
		$http.get(urlAddress + "cities").then(response => {
			$scope.cities = response.data;
		});
	};

	$scope.loadDistricts = function () {
		if ($scope.city !== null && $scope.city === '') {
			$scope.address = $scope.city.name;
			$scope.districts = [];
			$scope.wards = [];
			return;
		}

		$http.get(urlAddress + $scope.city.code + "/districts").then(function (response) {
			$scope.districts = response.data;
			$scope.district = null;
			$scope.wards = [];
			$scope.setAddress();
		});

	};

	$scope.loadWards = function () {
		if (!$scope.district) {
			$scope.wards = [];
			return;
		}
		$http.get(urlAddress + $scope.district.code + "/wards").then(function (response) {
			$scope.wards = response.data;
			$scope.setAddress();
		});
	};

	$scope.setAddress = function () {
		if ($scope.ward != null) {
			$scope.formRegister.address = '';
			$scope.formRegister.address = $scope.ward.path_with_type;
		}
	}

	$scope.loadCities();
});

app.controller('AdminCategoryController', function ($scope, $http) {
	$scope.categories = [];
	$scope.form = {};
	var url = `${host}/category`;

	$scope.reset = function () {
		$scope.form = {};
	}

	$scope.loadAll = function () {
		$http.get(url).then(resp => {
			$scope.categories = resp.data;
		});
	}

	$scope.edit = function (id) {
		$http.get(url + "/" + id).then(resp => {
			$scope.form = resp.data;
		});
	}

	$scope.create = function () {
		$http.post(url, $scope.form).then(() => {
			$scope.reset();
			$scope.loadAll();
		});
	}
	$scope.update = function () {
		$http.put(url, $scope.form).then(() => {
			$scope.reset();
			$scope.loadAll();
		});
	}

	$scope.delete = function (id) {
		var url = `${host}/category/${id}`;
		$http.delete(url).then(() => $scope.findAll());
	}
	$scope.loadAll();
});

app.controller('CommentController', function ($scope, $http) {
	$scope.commentDTOs = [];
	$scope.commentForm = {};

	$scope.productId = angular.element(document.getElementById('productContainer')).attr('data-product-id');

	const url = `${host}/comment`;

	$scope.loadAll = function () {
		$http.get(url).then(resp => {
			$scope.commentDTOs = resp.data;
		})
	}

	$scope.toggleReplyForm = function (comment) {
		comment.showReplyForm = !comment.showReplyForm;
	};

	$scope.postComment = function () {
		var product = {};
		product.productId = $scope.productId;
		$scope.commentForm.product = product;
		$scope.commentForm.parentCommentId = 0;
		$scope.commentForm.commentDate = new Date();

		$http.post(url, $scope.commentForm).then(() => {
			$scope.findByProductId($scope.productId);
			$scope.resetForm();
		});
	}

	$scope.postSubComment = function (parentCommentId) {
		var product = {};
		product.productId = $scope.productId;
		$scope.commentForm.product = product;
		$scope.commentForm.parentCommentId = parentCommentId;
		$scope.commentForm.commentDate = new Date();

		$http.post(url, $scope.commentForm).then(() => {
			$scope.findByProductId($scope.productId)
			$scope.resetForm();
		});
	}

	$scope.findByProductId = function (productId) {
		$http.get(url + '/' + productId).then(resp => {
			$scope.commentDTOs = resp.data;
		})
	}
	$scope.delete = function (commentId) {
		console.log(url + '/' + commentId);
		$http.delete(url + '/' + commentId).then(() => {
			$scope.findByProductId($scope.productId);
		});
	}
	$scope.resetForm = function () {
		$scope.commentForm = {};
	}
	// Khởi tạo dữ liệu ban đầu
	$scope.findByProductId($scope.productId);
});

app.controller('UserPurchaseController', function ($scope, $http) {
	$scope.orders = [];
	$scope.orderitems = [];
	$scope.filterOptions = 'All';
	const url = `${host}/order`;

	$scope.loadAll = function () {
		const url = `${host}/order`;
		$http.get(url).then(resp => {
			$scope.orders = resp.data;
		});
	}
	$scope.loadOrderStatus = function () {
		const url = `${host}/order/OrderStatus`;
		$http.get(url).then(resp => {
			$scope.orderStatusOptions = resp.data;
		});
	}
	$scope.getOrderItems = function (order) {
		$scope.orderitems = [];
		$scope.orderitems = order.orderItems;
	}


	$scope.getAmount = function (orderItems) {
		return orderItems.reduce(function (total, product) {
			return total + product.product.price * product.quantity;
		}, 0);
	}
	$scope.message = '';
	$scope.showDeleteButton = false;

	$scope.updateStatus = function (orderId, newStatus) {
		var url = `${host}/order/${orderId}`;
		$scope.form = { orderId: orderId, status: newStatus };

		$http.put(url, $scope.form).then(
			function (response) {
				// Xử lý phản hồi từ server nếu cần
				console.log(response.data);

				// Cập nhật message và hiển thị modal sau khi cập nhật thành công
				$scope.message = 'Hóa đơn "' + orderId + '" được cập nhật sang trạng thái "' + newStatus + '"';
				$('#deleteConfirmationModal').modal('show');

				// Nếu cần, bạn có thể gọi $scope.loadAll() ở đây hoặc ở bất cứ nơi nào phù hợp
			},
			function (error) {
				// Xử lý lỗi nếu cần
				console.error('Error updating order status:', error);
			}
		);
	};

	$scope.applyFilter = function (statusOption) {
		$scope.filterByStatus = (statusOption !== 'All');
		$scope.filterOptions = statusOption;
	};
	$scope.loadOrderStatus();
	$scope.loadAll();
});

app.controller('TestController', function ($scope, $http) {
	var cities = [];

	$scope.loadAll = function () {
		$http.get("\\data\\vietnam_location\\cities.json").then(resp => {
			console.log(resp.data);
		});
	}

	$scope.loadAll();
});

