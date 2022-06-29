package com.laptrinhjavaweb.utils;

import javax.servlet.http.HttpServletRequest;

/*
sessin là một phiênnnn làm việc,  việc. Nó là cách đơn giản để lưu trữ 1 biến và khiến biến đó có thể tồn tại từ trang này sang trang khác.
Nếu như với các biến thông thường, khi trang web bất kỳ bắt đầu thực thi, biến đó sẽ được cấp phát bộ nhớ, lưu giá trị và thu hồi vùng nhớ sau khi
trang kết thúc.
 Session sẽ khác, nó có thể được tạo ra, tồn tại trên server , có thể xuyên từ trang này sang trang khác, chỉ mất đi khi ta xóa nó hoặc
hết tuổi thọ (quá thời gian load dữ liệu hoặc thoát khỏi địa chỉ trang-đóng ứng dụng).

đó là lí do tại sao SessionUtil để static/

Tóm lại session giúp chúng ta lưu trữ lại trạng thái của người dùng.


*/

public class SessionUtil {
    public static SessionUtil sessionUtil = null;

    //https://www.youtube.com/watch?v=NEub4oQgPGM&list=PLW1k06REn7HsHn6D6e27gtFm-HXrZDLPX&index=33 26:05
    public static SessionUtil getInstance() {
        if (sessionUtil == null) {
            sessionUtil = new SessionUtil();
        }
        return sessionUtil;
    }


    public void putValue(HttpServletRequest request, String key, Object value) {// duy trì thông tin người dùng
        request.getSession().setAttribute(key, value);
    }

    public Object getValue(HttpServletRequest request, String key) {

        return request.getSession().getAttribute(key);
    }

    public void removeValue(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }
}
