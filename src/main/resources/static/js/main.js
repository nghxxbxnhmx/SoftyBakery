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
app.controller('CommentController', function ($scope, $http) {
	$scope.form = {};
    $scope.items = [];
    $scope.key = null;
    	
    $scope.load_all = function () {
        var url = `${host}/comments`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

   
  
    $scope.delete = function (commentId) {
        var url = `${host}/comments/${commentId}`;
        $http.delete(url).then(resp => {
            var index = $scope.items.findIndex(item => item.commentId == commentId);
            $scope.items.splice(index, 1);
            $scope.reset();
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

    // Thực hiện tải toàn bộ students
    $scope.load_all();
   
});
app.controller('comment', function ($scope, $http) {
    $scope.form = {};
    $scope.items = [];
    $scope.key = null;
    	
    $scope.load_all = function () {
        var url = `${host}/comments`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

   
  
    $scope.delete = function (id) {
        var url = `${host}/comments/${commentId}`;
        $http.delete(url).then(resp => {
            var index = $scope.items.findIndex(item => item.commentId == commentId);
            $scope.items.splice(index, 1);
            $scope.reset();
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

    // Thực hiện tải toàn bộ students
    $scope.load_all();
    $scope.reset();
});
app.controller('review', function ($scope, $http) {
    $scope.form = {};
    $scope.items = [];
    $scope.key = null;

    $scope.reset = function () {
        $scope.form = { gender: true, country: 'VN' };
        $scope.key = null;
    }

	
    $scope.load_all = function () {
        var url = `${host}/review`;
       
        $http.get(url).then(resp => {
            $scope.items = resp.data;
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.update = function () {
        var key = $scope.key;
        var url = `${host}/review/${$scope.form.reviewId}`;
        $http.put(url, $scope.form).then(resp => {
            var index = $scope.items.findIndex(item => item.reviewId == $scope.form.reviewId);
            $scope.items[index] = resp.data;
            $scope.reset();
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.edit = function (reviewId) {
        var url = `${host}/review/${reviewId}`;
        $http.get(url).then(resp => {
            $scope.form = resp.data;
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.create = function () {
        var url = `${host}/review`;
        $http.post(url, $scope.form).then(resp => {
            $scope.reset();
            $scope.load_all();
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

    $scope.delete = function (reviewId) {
        var url = `${host}/review/${reviewId}`;
        $http.delete(url).then(resp => {
            var index = $scope.items.findIndex(item => item.reviewId == reviewId);
            $scope.items.splice(index, 1);
            $scope.reset();
            console.log("Success", resp);
        }).catch(error => {
            console.log("Error", error);
        });
    }

    // Thực hiện tải toàn bộ students
    $scope.load_all();
    $scope.reset();
});
app.controller('ProductController', function ($scope, $http, $filter, $location, $routeParams) {
	// product
	$scope.spe = [];
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
		var url = `${host}/user`;
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
			$scope.userInfo.admin = resp.data.admin;
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
	// function loadAllSpe() {
	// 	var url = `${host}/product/specialoption`;
	// 	return $http.get(url).then(response => {
	// 		$scope.spe = response.data;
		
	// 	});
	// }
	$scope.loadAllSpe = function () {
		var url = `${host}/product/specialoption`;
		$http.get(url).then(resp => {
			$scope.spe = resp.data;
		});
	}

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
	$scope.loadAllSpe ();
});
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
app.controller('OrderPending', function ($scope, $http) {
	$scope.OrderList = [];
	$scope.form = {};
	$scope.loadAll = function () {
		const url = `${host}/order/pending`;
		$http.get(url).then(resp => {
			$scope.OrderList = resp.data;
		});
	}
	$scope.loadAll();
});
app.controller('OrderRefun', function ($scope, $http) {
	$scope.OrderList = [];
	$scope.form = {};
	$scope.loadAll = function () {
		const url = `${host}/order/refun`;
		$http.get(url).then(resp => {
			$scope.OrderList = resp.data;
		});
	}
	$scope.loadAll();
});
app.controller('OrderComfi', function ($scope, $http) {
	$scope.OrderList = [];
	$scope.form = {};
	$scope.loadAll = function () {
		const url = `${host}/order/comfi`;
		$http.get(url).then(resp => {
			$scope.OrderList = resp.data;
		});
	}
	$scope.loadAll();
});
app.controller('OrderDely', function ($scope, $http) {
	$scope.OrderList = [];
	$scope.form = {};
	$scope.loadAll = function () {
		const url = `${host}/order/dely`;
		$http.get(url).then(resp => {
			$scope.OrderList = resp.data;
		});
	}
	$scope.loadAll();
});
app.controller('OrderShipping', function ($scope, $http) {
	$scope.OrderList = [];
	$scope.form = {};
	$scope.loadAll = function () {
		const url = `${host}/order/shipping`;
		$http.get(url).then(resp => {
			$scope.OrderList = resp.data;
		});
	}
	$scope.loadAll();
});
app.controller('OrderCanceled', function ($scope, $http) {
	$scope.OrderList = [];
	$scope.form = {};
	$scope.loadAll = function () {
		const url = `${host}/order/cancel`;
		$http.get(url).then(resp => {
			$scope.OrderList = resp.data;
		});
	}
	$scope.loadAll();
});
// app.controller('OrderHistoryController', function ($scope, $http) {

// 	$scope.modifyCart = function (id, method) {
// 		// console.log(`${host}/cart/${method}/${id}`);
// 		$http.get(`${host}/cart/${method}/${id}`).then(function () {
// 			$scope.loadAllCart();
// 		});
// 	};
// 	$scope.OrderList = [];
// 	$scope.form = {};
// 	$scope.loadAll = function () {
// 		const url = `${host}/order`;
// 		$http.get(url).then(resp => {
// 			$scope.OrderList = resp.data;
// 		});
// 	}
// 	$scope.loadAll();
// });
app.controller('OrderHistoryController', function ($scope, $http) {

    $scope.modifyCart = function (id, method) {
        $http.get(`${host}/cart/${method}/${id}`).then(function () {
          
            $scope.addToCart(id); // Gọi hàm addToCart để lưu sản phẩm vào giỏ hàng
        });
    };

    // Hàm để thêm sản phẩm vào giỏ hàng (có thể thay đổi tùy theo cách bạn quản lý giỏ hàng)
    $scope.addToCart = function(productId) {
    
        if (!$scope.cart) {
            $scope.cart = []; // Khởi tạo giỏ hàng nếu chưa tồn tại
        }
        $scope.cart.push(productId); // Thêm sản phẩm vào giỏ hàng
        console.log("Sản phẩm đã được thêm vào giỏ hàng:", productId);
    };

    $scope.OrderList = [];
    $scope.form = {};

    $scope.loadAll = function () {
        const url = `${host}/order`;
        $http.get(url).then(resp => {
            $scope.OrderList = resp.data;
        });
    };

    $scope.loadAll();
});
app.controller('OrderController', function ($scope, $http) {
	$scope.products = [];
	$scope.category = [];
	$scope.form = {};
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
	const urlUser = `${host}/user`;

	function getUser() {
		var url = urlUser;
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
		$http.put(urlUser, $scope.form)
			.then(function (resp) {
				// Xử lý phản hồi thành công từ máy chủ
				$scope.form = resp.data;
			})
			.catch(function (error) {
				// Xử lý lỗi
				console.error("Error:", error);
				// Hiển thị thông báo lỗi cho người dùng (tùy bạn)
			});
	}
	$scope.loadCities = function () {
		$http.get(url + "cities").then(function (response) {
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
		if(method==="Add") {
			$http.post(url, $scope.form).then( () => {
				$scope.loadAll();
				$scope.reset();
			});
		}
		if(method==="Update") {
			$http.put(url, $scope.form).then( () => {
				$scope.loadAll();
				$scope.reset();
			});
		}
		
	}

	$scope.delete = function (productId) {
		const url = `${host}/product/${productId}`;
		$http.delete(url).then( () => {
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
app.controller('AdminOrderController', function ($scope, $http) {
	$scope.orders = [];
	$scope.oiList = [];

	$scope.loadAll = function () {
		const url = `${host}/order`;
		$http.get(url).then(resp => {
			$scope.orders = resp.data;
			console.log(resp.data);
		});
	}

	$scope.updateStatus = function (id) {
		const url = `${host}/order/${id}/updateStatus`;
		$http.put(url)
			.then(function (response) {
				console.log('Trạng thái của đơn hàng đã được cập nhật.');
				$scope.loadAll();
			})
			.catch(function (error) {
				console.error('Lỗi khi cập nhật trạng thái của đơn hàng:', error);
			});
	};

	$scope.getStatusClass = function (status) {
		switch (status) {
			case 'Đang chờ':
				return 'btn btn-primary';
			case 'Đã giao':
				return 'btn btn-success';
			case 'Đã hủy':
				return 'btn btn-secondary';
			default:
				return 'btn';
		}
	};

	$scope.getOrderItemByOrderId = function (orderId) {
		const url = `${host}/order/detail/${orderId}`;
		$http.get(url).then(resp => {
			$scope.oiList = resp.data;
			console.log(resp.data);
		});
	}
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
		$http.put(url, $scope.form).then(() => $scope.loadAll());
	}

	$scope.delete = function (id) {
		$http.delete(url + '/' + id).then(() => $scope.loadAll()).then(() => $scope.reset());
	}
	$scope.loadForm = function (coupon) {
		coupon.startDate = new Date(coupon.startDate);
		coupon.endDate = new Date(coupon.endDate);
		$scope.form = angular.copy(coupon);
	};

	$scope.reset = function () {
		$scope.form = {};
	}
	//khởi tạo dữ liệu ban đầu
	$scope.loadAll();
});

