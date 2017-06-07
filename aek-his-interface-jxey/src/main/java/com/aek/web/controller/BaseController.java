package com.aek.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.mb.common.rest.ResultBean;
import com.aek.web.controller.editor.DateEditor;
import com.aek.web.controller.editor.DoubleEditor;
import com.aek.web.controller.editor.IntegerEditor;
import com.aek.web.controller.editor.LongEditor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mb.common.util.EncryptionUtils;
import com.mb.common.util.SystemResource;

public abstract class BaseController {
	@Autowired
	protected ResourceBundleMessageSource _res;

	// @Autowired
	// protected SpringContextHolder _contextHolder;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    binder.setAutoGrowCollectionLimit(1024);
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(long.class, new LongEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
		binder.registerCustomEditor(Date.class, new DateEditor());
	}

	protected ModelAndView ajaxDone(int statusCode, String message) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		return mav;
	}

	protected ModelAndView ajaxDoneSuccess(String message) {
		return ajaxDone(200, message);
	}

	protected ModelAndView ajaxDoneError(String message) {
		return ajaxDone(300, message);
	}

	protected String getMessage(String code) {
		return this.getMessage(code, new Object[] {});
	}

	protected String getMessage(String code, Object arg0) {
		return getMessage(code, new Object[] { arg0 });
	}

	protected String getMessage(String code, Object arg0, Object arg1) {
		return getMessage(code, new Object[] { arg0, arg1 });
	}

	protected String getMessage(String code, Object arg0, Object arg1, Object arg2) {
		return getMessage(code, new Object[] { arg0, arg1, arg2 });
	}

	protected String getMessage(String code, Object arg0, Object arg1, Object arg2, Object arg3) {
		return getMessage(code, new Object[] { arg0, arg1, arg2, arg3 });
	}

	protected String getMessage(String code, Object[] args) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		Locale locale = localeResolver.resolveLocale(request);

		return _res.getMessage(code, args, locale);
	}

	public void responseJson(Object obj, HttpServletResponse response) {

		String result = "";
		if (null != obj) {
			Gson gson = null;
			if (obj instanceof Map) {
				gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			} else {
				gson = new Gson();
			}
			result = gson.toJson(obj);
		}

		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(result);
		} catch (IOException e) {

		} finally {
			out.flush();
			out.close();
		}
	}

	protected void removeSessionUser(HttpSession session) {
		session.removeAttribute("SESSION_USER");
	}
	
	/**
	 * 
	 * @param token
	 * @param comeFrom
	 * @return
	 * @date 2016年4月13日
	 */
	protected boolean checkSysCode(String token, int comeFrom) {
	    String sysKey = null;
	    switch (comeFrom) {
	        case 1 : sysKey = "cfs";
	        break;
	        case 2 : sysKey = "yibei";
	        break;
	        case 3 : sysKey = "yiya";
	        break;
	        case 4 : sysKey = "aiyikang";
	        break;
	        case 5 : sysKey = "ufs";
	        break;
	        case 6 : sysKey = "ums"; 
	        break;
	        default : return false;
	    }
	    String sysCode = SystemResource.getConfig("sys." + sysKey + ".code");
	    if (EncryptionUtils.encodeSysCode(sysCode).equals(token)) {
	        return true;
	    }
	    return false;
	}
	
	protected ResultBean miniUiResutlJson(int code, String msg) {
        ResultBean rs = new ResultBean();
        rs.setCode(code);
        rs.setMsg(msg);
        return rs;
    }
    
    protected ResultBean optCommonResult(int optNum) {
        ResultBean rs = new ResultBean();
        if (optNum != 1) {
            rs.setCode(0);
            rs.setMsg("FAIL");
        }
        return rs;
    }

}
