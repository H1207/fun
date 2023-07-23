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
		location="C:/heg/web/work/hamaProject/WebContent/event/upload"
)
@WebServlet("/ev_tor_form_in")
public class EvTorFormInCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public EvTorFormInCtrl() {super();  }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");	
    	
 
    	//������ �������� ����, ����, ����, Ŀ���Ҹ�ī�� �ε���
    	
		//������
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// �α��ΰ˻�
		HttpSession session = request.getSession();
		MemberInfo loginInfo = (MemberInfo)session.getAttribute("loginInfo");	
		if(loginInfo == null) {
			out.println("<script>");
			out.println("alert('�α��� ���� ����Ͻ� �� �ֽ��ϴ�.');");
			out.println("location.replace('login_form.jsp?url=ev_tor_form_in');");
			out.println("</script>");
			out.close();
		}
		String miid = loginInfo.getMi_id();
		
		

		//����
		String title = request.getParameter("title");
		if(title != null) title= getStr(title);
		
		//����
		String content = request.getParameter("content");
		if(content  != null) content = getStr(content);
			
		//Ŀ���Ҹ�ī�� �ε��� 
		int pmcidx=Integer.parseInt(request.getParameter("pmcidx"));

	
		//�������� �� �̸� 
		String isFile = request.getParameter("isFile");
		String  uploadFileName = ""; 
		if(isFile.equals("y")) { 
			Part part = request.getPart("file1");

			String contentDisposition = part.getHeader("content-disposition");
			//��) form-data; name="file1"; filename="���ε������ϸ�.Ȯ����"	
			System.out.println("contentDisposition : "+ contentDisposition);
			// Ȯ�ο��ַܼα�
			uploadFileName = getUploadFileName(contentDisposition);
			//uploadFileName = ���� �̸�
			part.write(uploadFileName); //��������
		}
		
		
		//Ȯ�ο�
		System.out.println( "EvTorFormInCtrl Ȯ��"+
					"/title=" + title +
					"/content=" +  content+ 
					"/pmcidx=" +  pmcidx+ 
					"/uploadFileName=" +  uploadFileName
		);
		
			
		EvCusTor ect = new EvCusTor();
		
		ect.setPmc_idx(pmcidx);
		ect.setMi_id(miid);
		ect.setEct_img1(uploadFileName);
		ect.setEct_title(title);
		ect.setEct_content(content);
		
		
		EvTorFormSvc evTorFormSvc = new EvTorFormSvc();
		
		int result =  evTorFormSvc.torInsert(miid, ect);
		
		if(result!=1) {
			out.println("<script>");
			out.println("alert('��ʸ�Ʈ�� �������� �ʾҽ��ϴ�. \\n �ٽ� �õ����ּ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		response.sendRedirect("ev_tor_list?kind=a"); 

    	
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
  	private String getStr(String str) {
		//����ڰ� �Է��� ���ڿ��� ���� ó�� �ؼ� �����ϴ� �޼ҵ�
		//��� �����ǹ����� null�� ���� �ȵ� null���� nullpointException���� 
		return str.trim().replace("'", "''").replace("<", "&lt;");
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
