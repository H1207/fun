package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@MultipartConfig(
		fileSizeThreshold=0,
		location="E:/heg/web/work/hamaProject/WebContent/product/upload"
		// �������� �����θ� ��� ���� ��  �׷��� ���ε��� ������ ���⿡ �� �ִ�. ��θ� �ٵ� �ٲ���� 
		//location="E:/heg/web/work/hamaProject/WebContent/product/upload"
		
)
@WebServlet("/product_custom_in")
public class ProductCustomInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProductCustomInCtrl() {super();  }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");	
		//������
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//�˻��Ұ�. 1.��ī���̸��˻�. 2.�α��ΰ˻�  3. ���ΰ˻�. 4. ���ϰ˻�

		
		//1. ��ī���̸��˻�. jsp���� onsubmit(true) �ؼ� �˻� ���ص� �� 
		String name = request.getParameter("name");
		//if(name==null||name.equals("")) {
			//out.println("<script>");
			//out.println("alert('��ī�� �̸��� �Է����ּ���.');");
			//out.println("history.back();");
			//out.println("</script>");
			//out.close();
		//}
		
		//2. �α��ΰ˻�
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");	
		if(loginInfo == null) {
			
			out.println("<script>");
			out.println("alert('�α��� ���� ����Ͻ� �� �ֽ��ϴ�.');");
			out.println("location.replace('login_form.jsp?url=product_custom_form');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
		
		
		//3. ���ΰ˻�
		int toppingcnt=Integer.parseInt(request.getParameter("toppingcnt"));
		//���� ���� �� (���� �Ȱ������0 / 1 / 2)
		String tp1 = ""; String tp2 = ""; //���ξ��̵�  ������ ���� 
		
		if(toppingcnt==1) { //���� �ϳ���,
			tp1 = request.getParameter("topping"); 
		}else if(toppingcnt==2) { //���� �� ���϶��� �迭�� �޾ƿ´�.
			String[] tp =  request.getParameterValues("topping");
			tp1 = tp[0];
			tp2 = tp[1];	
		}
			
			
		//4.���ϰ˻�
		String isFile = request.getParameter("isFile");
		String  uploadFileName = ""; //������ �ø� ���͸� �̹��� �̸��� ������ ���� 
		if(isFile.equals("y")) { //������ ���͸��� ���ε� ���� ���� ������	
			//������ ���ε��� ���͸� ���� 
			Part part = request.getPart("file1");
			//���ε� �Ǵ� ������ Part�� �ν��Ͻ��� ���� 

			String contentDisposition = part.getHeader("content-disposition");
			//��) form-data; name="file1"; filename="���ε������ϸ�.Ȯ����"	
			System.out.println("contentDisposition : "+ contentDisposition);
			// Ȯ�ο��ַܼα�
			uploadFileName = getUploadFileName(contentDisposition);
			//uploadFileName = ���� �̸�
			part.write(uploadFileName); //��������
		}
		
		
		
		//���� �� ���� ���� �絵,��ǿɼ�,�ʸ���, ��ī�ո�(�����������̵�) ������ ���
		int sweet =Integer.parseInt(request.getParameter("sweet"));
		String vg =request.getParameter("vg");
		String pl =request.getParameter("pl");
		String taste =request.getParameter("taste");
		
		
		//Ȯ�ο�
		System.out.println( "ProductCustomInCtrl Ȯ��"+
				"/�̸�=" + name +
				"/��=" +  taste+ 
				"/�絵=" +  sweet+ 
				"/��ǿɼ�=" + vg + 
				"/�ʸ���=" +  pl+ 
				"/����1=" + tp1 + 
				"/����2=" + tp2 + 
				"/���͸� �̹���=" + uploadFileName + 
				"/���̵�=" + miid 
		);
		
		
		//��ƾ��� �� �����ϱ� ProductCustom�� �ν��Ͻ��� �����ؼ� ������
		ProductCustom pc =  new ProductCustom();
		pc.setMi_id(miid);
		pc.setPmc_name(name);
		pc.setPmc_sugar(sweet);
		pc.setPmc_vg(vg);
		pc.setPmc_pl(pl);
		pc.setPmc_img(uploadFileName);
		pc.setPmc_tp1(tp1);
		pc.setPmc_tp2(tp2);
		pc.setPi_id(taste);
		
		ProductCustomSvc productCustomSvc = new ProductCustomSvc();	
		int result = 0;
		result = productCustomSvc.customInsert(miid,pc);
	
		//result ����� ���� �����°� �޶��� 
		
		if(result!=1) {
			out.println("<script>");
			out.println("alert('Ŀ���� ��ī���� ��ī���� ��ϵ��� �ʾҽ��ϴ�. \\n �ٽ� �õ����ּ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		
		response.sendRedirect("product_custom_list"); // �۹�ȣ
		
		
		
	}
	//�ø� ������ �̸��� ������ �ִµ� �ʿ��� �޼ҵ� �ǵ�x
	private String getUploadFileName(String contentDisposition){
		
		String uploadFileName = null;
		String[] contentSplitStr = contentDisposition.split(";");
		//���� �ݷ��� �������� split
		
		int fIdx = contentSplitStr[2].indexOf("\""); //����ǥ�� ��ġ�� ã�� 
		int sIdx = contentSplitStr[2].lastIndexOf("\""); //����������ǥ��ġ��ã��
		
		uploadFileName = contentSplitStr[2].substring(fIdx+1, sIdx);
		return uploadFileName;
		//��) contentSplitStr[2]�� �����ݷ����� �߶��� ������ filename="���ε������ϸ�.Ȯ����" 
		//�� �ǰ� �׷��� ù��° " �� ������ "�� ã�Ƽ� �߶���� �Ű� ���ε������ϸ�.Ȯ���ڰ�
	// �ᱹ ���ε������ϸ�.Ȯ���� = uploadFileName �� �ȴ� 
	}

}
