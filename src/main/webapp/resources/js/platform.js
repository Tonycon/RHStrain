function judgeDevice() {
	var sUserAgent = navigator.userAgent.toLowerCase();
	var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
	if (bIsIpad) {
		return "ipad";
	}
	var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
	if (bIsIphoneOs) {
		return "iphone";
	}
	var bIsAndroid = sUserAgent.match(/android/i) == "android";
	if (bIsAndroid) {
		return "android";
	}
	var ua = window.navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == 'micromessenger') {
		return "weixin";
	}
	return "other";
}