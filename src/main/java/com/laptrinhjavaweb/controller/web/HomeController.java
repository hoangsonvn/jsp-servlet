package com.laptrinhjavaweb.controller.web;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.SessionUtil;

@WebServlet(urlPatterns = {"/trang-chu", "/dang-nhap", "/thoat", "/m"})
public class HomeController extends HttpServlet {

    /**
     *
     */
    ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
    private static final long serialVersionUID = 2686801510274002166L;
    @Inject
    private INewService newService;
    @Inject
    private ICategoryService categoryService;
    @Inject
    private IUserService userService;

    //	private INewService newService;
//
//	public HomeController(INewService newService) {
//		newService = new NewService();
//	}
// bean.xml / nhìn kĩ 6 dòng trên tức mỗi khi request trang chủ nó sẽ tạo ra 1 NewService , nếu nhiều request thì tốn
// vì đối tượng NewService đc tạo ra rồi, 100 lần tạo ra 100 cái là phí, nên cần bean.xml https://www.youtube.com/watch?v=ZN_Dszk7Czk&list=PLW1k06REn7HsFAU3TZpBOV6suEsjGjeBa&index=7
//phút 5:20. thằng bean.xml quản lí đối tượng cho depencency. tức tạo cho 1 thùng chứa, request ban đầu này nó sẽ cầm đối tượng new service ném vào trong bean
// đến request thứ 2 và nó càn xài newservice, nếu chưa thì tạo, nếu rồi thì xài.
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");//lấy ra giá trị action trên url
        if (action != null && action.equals("login")) {// nếu bằng login thì thực hiện tiếp.
            String message = request.getParameter("message");
            String alert = request.getParameter("alert");
            if (message != null && alert != null) {
                request.setAttribute("message", resourceBundle.getString(message));
                request.setAttribute("alert", alert);
/* thằng getParameter lấy giá trị trên thanh url, thằng setAttribute đặt giá trị ${alert} = alert"danger" ,
 đặt ${message} thành giá trị resourceBundle.getString(message) tức là resourceBundle.getString(username_password_invalid)"Username or Password is invalid" */
            }
            RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
            rd.forward(request, response);
        } else if (action != null && action.equals("logout")) {
            SessionUtil.getInstance().removeValue(request, "USERMODEL");// co dong nay de xoa di UseModel neu k co thif khi
            // thoat ra ve trang chu no van hien , xin chao, admin
            response.sendRedirect(request.getContextPath() + "/trang-chu");
//           RequestDispatcher rd = request.getRequestDispatcher("/views/web/home.jsp");
//           rd.forward(request, response);
        } else {
            //request.setAttribute("categories", categoryService.findAll());
            RequestDispatcher rd = request.getRequestDispatcher("/views/web/home.jsp");
            rd.forward(request, response);
        }

//		Long id =1L;
//	request.setAttribute("news", newService.suaDemo(id));
//////
//		request.setAttribute("news", newService.findAll());
        // https://www.youtube.com/watch?v=unUqbDiNicw&list=PLW1k06REn7HsFAU3TZpBOV6suEsjGjeBa&index=3
        // phút 30
//	 khi chạy trang hôm thì load catogery lên nên nhúng inject vào home
//		request.setAttribute("categories", categoryService.findAll());

//		Long categoryId = 1L;
//		request.setAttribute("news", newService.findByCategoryId(categoryId));
//
//	String title =" bai viet 4";
//	String content = " bai viet 40";
//	Long categoryId = 5L;// khoong the dat categoryid bang 20 vi id trong bang category chua den 20" TH minh chua nhap den 20"
//	Long id=1L;
//	NewModel newModel = new NewModel();
//	newModel.setTitle(title);
//	newModel.setContent(content);
//	newModel.setCategoryId(categoryId);
//newModel.setId(id);
//newService.saveInset(newModel);
//newService.save(newModel);
//////
////
//
//		RequestDispatcher rd = request.getRequestDispatcher("/views/web/home.jsp");
//		rd.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("login")) {
            UserModel model = FormUtil.toModel(UserModel.class, request);// map các field
            int status = 1;
            model.setStatus(status);
            model = userService.findByUserNameAndPasswordAndStatus(model.getUserName(), model.getPassword(),
                    model.getStatus());
            if (model != null) {

                SessionUtil.getInstance().putValue(request, "USERMODEL", model); //https://www.youtube.com/watch?v=JwiUxVmZJks&list=PLW1k06REn7HsHn6D6e27gtFm-HXrZDLPX&index=34 8:25
                //gọi thẳng tên lớp vì cha getInstance là static medthod thuộc lớp/.
                if (model.getRole().getCode().equals("USER")) {
                    response.sendRedirect(request.getContextPath() + "/trang-chu");

                } else if (model.getRole().getCode().equals("ADMIN")) {
                    response.sendRedirect(request.getContextPath() + "/admin-home");

                }
            } else {//trả link để xử lí thêm thông báo
                response.sendRedirect(request.getContextPath() + "/dang-nhap?action=login&message=username_password_invalid&alert=danger");
            }

        }
    }
}
