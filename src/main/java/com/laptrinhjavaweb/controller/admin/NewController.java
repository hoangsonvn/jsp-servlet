package com.laptrinhjavaweb.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.ICategoryService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.sort.Sorter;
import com.laptrinhjavaweb.utils.FormUtil;

@WebServlet(urlPatterns = {"/admin-new"})
public class NewController extends HttpServlet {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
    @Inject
    INewService newService;
    @Inject
    ICategoryService categoryService;
    /**
     *
     */
    private static final long serialVersionUID = 2686801510274002166L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //	NewModel model = new NewModel();

        NewModel model = FormUtil.toModel(NewModel.class, request);//đặt maxPageItem,page trùng với trong model để match
        String view = "";
//		String pageStr = request.getParameter("page");
//		String maxPageItemStr= request.getParameter("maxPageItem");
//
//		if (pageStr != null) {
//			model.setPage(Integer.parseInt(pageStr));
//		} else {
//			model.setPage(1);
//		}
//		if (maxPageItemStr != null) {
//			model.setMaxPageItem(Integer.parseInt(maxPageItemStr));
//		}
        if (model.getType().equals(SystemConstant.LIST)) {
            Pageble pageble = new PageRequest(model.getPage(), model.getMaxPageItem(), new Sorter(model.getSortName(), model.getSortBy()));

            model.setListResult(newService.findAll(pageble));
            //  CategoryModel ct = new CategoryModel();
//ct.setListResult(newService.findAll(pageble));// nếu gỡ <CategoryModel> trong CategoryModel thì có thể dùng được lệnh trên vì nó chỉ bắt buộc cùng kiểu chứ vẫn có thể kiểu khác như newmodel


            model.setTotalItem(newService.getTotalItem());// kyx thuat rut gon than so vaof package bai phan trang cuoi

            model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getMaxPageItem()));
            // request.setAttribute(SystemConstant.MODEL, model);
            String message = request.getParameter("message");
            String alert = request.getParameter("alert");
            if (message != null && alert != null) {
                request.setAttribute("message", resourceBundle.getString(message));
                request.setAttribute("alert", alert);
            }
            view = "/views/admin/new/list.jsp";
            /* đặt giá trị của SystemConstant.MODEL tức model tức ${model} trong list.jsp thành model  trong ngôn ngữ jsp*/
//            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/new/list.jsp");
//            rd.forward(request, response);
        } else if (model.getType().equals(SystemConstant.EDIT)) {
            if (model.getId() != null) {
                model = newService.findOne(model.getId());
            } else {//https://www.youtube.com/watch?v=FK4kZHVt9JA&list=PLW1k06REn7HsHn6D6e27gtFm-HXrZDLPX&index=39 27:50

            }
            String message = request.getParameter("message");
            String alert = request.getParameter("alert");
            if (message != null || alert != null) {
                request.setAttribute("message", resourceBundle.getString(message));
                request.setAttribute("alert", alert);
            }
            request.setAttribute("categories", categoryService.findAll());
            view = "/views/admin/new/edit.jsp";
//
//            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/new/edit.jsp");
//            rd.forward(request, response);
        }
        request.setAttribute(SystemConstant.MODEL, model);
        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);

//26.3 xem lại tại sao bỏ sorter vào interface
//thằng dưới là getPage() vì  offset nó tính ở findall., còn dưới nữa là offset vì thầy tính offset luôn rồi
        // 21:40 https://www.youtube.com/watch?v=FK4kZHVt9JA&list=PLW1k06REn7HsHn6D6e27gtFm-HXrZDLPX&index=39 21:40       Pageble pageble = new PageRequest(model.getPage(), model.getMaxPageItem(), new Sorter(model.getSortName(), model.getSortBy()));
        //	Integer offset = (model.getPage() - 1) * model.getMaxPageItem();

//		model.setListResult(newService.findAll(offset, model.getMaxPageItem(),model.getSortName(),model.getSortBy()));// đặt toàn bộ list vào newModel vì
//																				// trong newMOdel có kiểu
        // model.setTotalItem(model.getListResult().size());// ;12:30
        // https://www.youtube.com/watch?v=vQcdVAJSYOM&list=PLW1k06REn7HsHn6D6e27gtFm-HXrZDLPX&index=25
//21:40
//        model.setListResult(newService.findAll(pageble));
//        model.setTotalItem(newService.getTotalItem());// kyx thuat rut gon than so vaof package bai phan trang cuoi
//
//        model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getMaxPageItem()));
//        request.setAttribute(SystemConstant.MODEL, model);
//        /* đặt giá trị của SystemConstant.MODEL tức model tức ${model} trong list.jsp thành model  trong ngôn ngữ jsp*/
//        RequestDispatcher rd = request.getRequestDispatcher("/views/admin/new/list.jsp");
//        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }
}
