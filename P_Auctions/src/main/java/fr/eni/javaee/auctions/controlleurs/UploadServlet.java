package fr.eni.javaee.auctions.controlleurs;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class ServletUploadImage
 */
@WebServlet("/upload")
@MultipartConfig( fileSizeThreshold = 1024 * 1024, 
maxFileSize = 1024 * 1024 * 5, 
maxRequestSize = 1024 * 1024 * 5 * 5 )
		public class UploadServlet extends HttpServlet {
			
		    private static final long serialVersionUID = 1273074928096412095L;

		    
		    
		    
		    public String uploadPath;
		    
		    /*
		     * Si le dossier de sauvegarde de l'image n'existe pas, on demande sa création.
		     */ 
		    @Override
		    public void init() throws ServletException {
		        String physicalPath = ("/images");
		    	uploadPath = getServletContext().getRealPath(physicalPath);
		        File uploadDir = new File( uploadPath );
		       if ( ! uploadDir.exists() ) uploadDir.mkdir();
   
		   }
		       
		    /*
		     * Récupération et sauvegarde du contenu de chaque image.
		     */ 
		    @Override
		    protected void doPost(HttpServletRequest request, HttpServletResponse resp)
		            throws ServletException, IOException {
		        for ( Part part : request.getParts() ) {
		            String fileName = getFileName( part );
		            String fullPath = uploadPath + File.separator + fileName;
		            System.out.println(fullPath);
		            part.write( fullPath );
		        }
		    }
		    /*
		     * Récupération du nom du fichier dans la requête.
		     */
		    private String getFileName( Part part ) {
		        for ( String content : part.getHeader( "content-disposition" ).split( ";" ) ) {
		            if ( content.trim().startsWith( "filename" ) )
		                return content.substring( content.indexOf( "=" ) + 2, content.length() - 1 );
		        }
		        System.out.println("Default.file");
		        return "Default.file";
		    }
		    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/testupload.jsp");
				rd.forward(request, response);
		    }
	}
