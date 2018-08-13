package main.Services.Helpers;

import javax.servlet.http.HttpServletRequest;

public final class ParameterChecker {
//Review. We should not use request object like this. If not needed (method) remove.
    public static boolean checkParameters(HttpServletRequest request, String[] paramsSettings) {

        for (int i = 0; i < paramsSettings.length; i++) {
            if (request.getParameter(paramsSettings[i]) == null || request.getParameter(paramsSettings[i]).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
