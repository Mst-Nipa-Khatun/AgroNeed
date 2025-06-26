package com.nipa.agroneed.controller.view;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object exceptionType = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        int status = (statusCode != null) ? Integer.parseInt(statusCode.toString()) : 500;

        model.addAttribute("status", status);
        model.addAttribute("error", HttpStatus.valueOf(status));
        model.addAttribute("message", (errorMessage != null) ? errorMessage.toString() : "N/A");
        model.addAttribute("path", (requestUri != null) ? requestUri.toString() : "N/A");
        model.addAttribute("exceptionType", (exceptionType != null) ? exceptionType.toString() : "N/A");
        model.addAttribute("exception", (exception != null) ? exception.toString() : "N/A");

        return "error"; // Refers to templates/error/error.html
    }
}
