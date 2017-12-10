package com.mathiasgrimm.lib.routeconfig;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<Route> parseForResource(String path) throws Exception {
        String content = new String(this.getClass().getResourceAsStream(path).readAllBytes());

        return this.parse(content);
    }

    public List<Route> parse(String configContent) throws Exception
    {
        List<Route> routes = new ArrayList<>();
        String [] lines    = this.parseLines(configContent);

        int i       = 0;
        String line = "";

        try {
            for (i = 0; i < lines.length; i++) {
                line = lines[i];
                List<String> parts = this.parseParts(line);
                routes.add(this.mapPartsToRoute(parts));
            }
        } catch (Exception e) {
            throw new Exception("Parser error when parsing line " + (i + 1) + " content: " + line, e);
        }

        return routes;
    }

    public Route mapPartsToRoute(List<String> parts)
    {
        Route route = new Route();

        for (int i = 0; i < parts.size(); i++) {

            String part = parts.get(i);

            switch (i) {
                case 0: {
                    route.setHttpMethod(part);
                    break;
                }

                case 1: {
                    route.setUrlPattern(part);
                    break;
                }

                case 2: {
                    Pair<String,String> controllerAndMethod = this.parseController(part);
                    route.setController(controllerAndMethod.getKey());

                    List<String> method = this.parseMethod(controllerAndMethod.getValue());
                    route.setControllerMethod(method.get(0));

                    if (method.size() > 1) {
                        List<String> methodArgs = new ArrayList<>();

                        for (int j = 1; j < method.size(); j++) {
                            methodArgs.add(method.get(j));
                        }

                        route.setControllerMethodParams(methodArgs);
                    }

                    break;
                }

                case 3: {
                    route.setName(part);
                    break;
                }
            }
        }

        return route;
    }

    public List<String> parseMethod(String rawMethod)
    {
        rawMethod            = rawMethod.replaceAll("\\).*", "");
        List methodParts     = new ArrayList<String>();
        String [] methodName = rawMethod.split("\\(");

        if (methodName.length > 0) {
            methodParts.add(methodName[0]);
        }

        if (methodName.length > 1) {
            String methodParams = methodName[1];
            if (methodParams.length() < 1) {
                return methodParts;
            }

            String [] args = methodParams.split(",");

            for (String arg : args) {
                if (arg.length() > 0) {
                    methodParts.add(arg);
                }
            }
        }

        return methodParts;
    }

    public Pair<String,String> parseController(String part)
    {
        String [] parts = part.split("\\.");

        ArrayList<String> controllerParts = new ArrayList<>();

        for (int i = 0; i < (parts.length - 1); i++) {
            controllerParts.add(parts[i]);
        }

        String controller = String.join(".", controllerParts);
        String method     = parts[parts.length - 1];

        Pair<String,String> controllerAndMethod = new Pair<>(controller, method);

        return controllerAndMethod;
    }

    public List<String> parseParts(String line)
    {
        String uncommentedLine = line.replaceAll("#.*", "");

        List<String> uncomentedParts = new ArrayList<>();

        if (uncommentedLine.length() == 0) {
            return uncomentedParts;
        }

        String [] parts = uncommentedLine.split("\\s+");

        for (String part : parts) {
            uncomentedParts.add(part);
        }

        return uncomentedParts;
    }

    public String [] parseLines(String content)
    {
        String [] lines = content.split("\n");
        return lines;
    }
}
