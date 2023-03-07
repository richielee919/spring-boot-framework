package springboot.mssql.demo.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		
		long startTime = System.currentTimeMillis();
		
		filterChain.doFilter(requestWrapper, responseWrapper);
		
		long totalTime = System.currentTimeMillis() - startTime;
		
		String requestBody = getStringValue(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
		String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());
		
		LOGGER.info("\n"+
				"FINISHED PROCESSING : " + "\n"
				+ "METHOD={}; " + "\n"
				+ "REQUESTURI={}; " + "\n"
				+ "REQUEST PAYLOAD={}; " + "\n"
				+ "RESPONSE CODE={}; " + "\n"
				+ "RESPONSE={}; " + "\n"
				+ "TIME TAKEN={}",
				request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody,
				totalTime);
		
		responseWrapper.copyBodyToResponse();
	}

	private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
		try {
			return new String(contentAsByteArray, 0 , contentAsByteArray.length, characterEncoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
