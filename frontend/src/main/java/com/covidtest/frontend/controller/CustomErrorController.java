package com.covidtest.frontend.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller in charge to display error base on HTTP status code
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Display error based on HTTP code
     *
     * @param request The request
     * @param model The model to pass data to the view
     *
     * @return The error view template
     */
    @GetMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        int code = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());

        String message;
        String status;
        if (code == 403) {
            status = "Interdit";
            message = "Vous n'êtes pas autorisé à accéder à cette page.";
        }
        else if (code == 404) {
            status = "Ressource Non Trouvée";
            message = "La page que vous essayez d'atteindre n'existe pas.";
        }
        else if (code == 503) {
            status = "Service Indisponible";
            message = "Une erreur est survenue. S'il vous plait, contactez un administrateur.";
        }
        else if (code >= 500) {
            status = "Erreur Serveur";
            message = "Une erreur innatendue est survenue. S'il vous plait, contactez un administrateur.";
        }
        else {
            status = "Erreur";
            message = "Une erreur innatendue est survenue. Réessayez plus tard.";
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
