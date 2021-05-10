package com.covidtest.frontend.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        int code = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());

        String message;
        String status;
        if (code == 403) {
            status = "Forbbiden";
            message = "You are not allowed to reach this page.";
        }
        else if (code == 404) {
            status = "Page Not Found";
            message = "The page you want to reach doesn't exist.";
        }
        else if (code == 503) {
            status = "Service Unavailable";
            message = "An error has occurred. Please contact an administrator.";
        }
        else if (code >= 500) {
            status = "Server Error";
            message = "An unexpected error has occurred. Please contact an administrator.";
        }
        else {
            status = "Error";
            message = "An unexpected error has occurred. Please try again later.";
        }

        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("code", code);

        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
