package org.school.fee.support.security.shirotag;

import java.util.List;

import org.school.fee.support.utils.ShiroUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class PrincipalMethodModel implements TemplateMethodModelEx{

	public Object exec(List arguments) throws TemplateModelException {
		// TODO Auto-generated method stub
		return ShiroUtils.getCurrentUser();
	}

}
