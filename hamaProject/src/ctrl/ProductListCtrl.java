package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;



@WebServlet("/product_list")
public class ProductListCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductListCtrl() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		
		String where = ""; //�˻����� where�� 
		String pc = request.getParameter("pc");	// ��ǰ �з� , �˻����� ����
		if (pc != null && !pc.equals("")) {
			
			where += " and pc_id = '" + pc + "' ";
		}
		
		//���������� �Ǹż�(pi_sale), �ֱټ� (pi_date)
		String orderBy = ""; 
		String o = request.getParameter("o");
		if (o == null || o.equals(""))	o = "a"; 

		switch (o) {
		case "a" :	// ��� ����
			orderBy = " order by pi_date desc";	break;
		case "b" :	// �Ǹŷ���
			orderBy = " order by pi_sale desc";	break;
		}
		
		
		ProductListSvc productListSvc = new ProductListSvc();
		ArrayList<ProductInfo> productList = 
				productListSvc.getProductList(where, orderBy); //��ǰ����Ʈ������
		
		PageInfo pageInfo = new PageInfo(); //������ ���� o������ 
		pageInfo.setO(o);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("productList", productList);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/product/product_list.jsp");
				dispatcher.forward(request, response);
	
	
	
	
	
	
	
	
	
	}

}

















