package com.laptrinhjavaweb.controller.admin.api;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.utils.HttpUtil;
import com.laptrinhjavaweb.utils.SessionUtil;

@WebServlet(urlPatterns = {"/api-admin-new"})
public class NewApi extends HttpServlet {
    @Inject
    INewService newService;
    /**
     *
     */
    private static final long serialVersionUID = -7917857215122346974L;

    //	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");// set utf 8 để trả về k lỗi font
        response.setContentType("application/json");// khi thằng serve xử lý xong rồi , nó trả kết quả về cline dạng
        // json, để thằng cline biết là json thì nó hải dịnh nghĩa
        // applition/json
        NewModel newModel = HttpUtil.of(request.getReader()).toModel(NewModel.class);// toModel(NewModel.class) để máy
        // hiểu nó dạng NewModel
        // getReader đọc dữ liệu của máy request
        // cách chạy là thằng postman tạo lệnh post sending request, thằng
        // HttpUtil.of(request.getReader()) đọc request ra String. toModel chuyển nó về
        // theo kiểu class
        newModel.setCreatedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUserName());
        newModel = newService.saveInset(newModel);
//	newModel = newService.save(newModel);
        mapper.writeValue(response.getOutputStream(), newModel);// ghi du lieu ra
//	System.out.println(newModel);

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        NewModel newModel = HttpUtil.of(request.getReader()).toModel(NewModel.class);
        try {
            newModel.setModifiedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUserName());
        } catch (NullPointerException e) {

        }

        newModel = newService.update(newModel);
        mapper.writeValue(response.getOutputStream(), newModel);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        NewModel newModel = HttpUtil.of(request.getReader()).toModel(NewModel.class);
        newService.delete(newModel.getIds());
        mapper.writeValue(response.getOutputStream(), "{}");
    }
}
