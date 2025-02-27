package edu.icet.util;

import com.google.inject.*;
import edu.icet.service.custom.LoginService;
import edu.icet.service.custom.SignupService;
import edu.icet.service.custom.impl.LoginServiceImpl;
import edu.icet.service.custom.impl.SignupServiceImpl;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LoginService.class).to(LoginServiceImpl.class);
        bind(SignupService.class).to(SignupServiceImpl.class);
    }
}
