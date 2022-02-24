package ${package.Controller};


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ${package.Service}.${table.serviceName};

/**
*  ${table.controllerName}
*  @author ${author}
*  @since ${date}
*/
@RestController
public class ${table.controllerName} {

    @Resource
    private ${table.serviceName} ${table.entityPath}Service;

}

