package com.smartbear.readyapi.client.teststeps.soaprequest;

import com.smartbear.readyapi.client.assertions.NotSoapFaultAssertionBuilder;
import com.smartbear.readyapi.client.assertions.SchemaComplianceAssertionBuilder;
import com.smartbear.readyapi.client.assertions.SoapFaultAssertionBuilder;
import com.smartbear.readyapi.client.model.SoapParameter;
import com.smartbear.readyapi.client.model.SoapRequestTestStep;
import com.smartbear.readyapi.client.teststeps.TestStepTypes;
import com.smartbear.readyapi.client.teststeps.request.HttpRequestStepBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.smartbear.readyapi.client.Validator.validateNotEmpty;

public class SoapRequestStepBuilder extends HttpRequestStepBuilder<SoapRequestStepBuilder, SoapRequestTestStep> {

    private List<SoapParameter> parameters = new ArrayList<>();

    public SoapRequestStepBuilder() {
        super(new SoapRequestTestStep(), TestStepTypes.SOAP_REQUEST.getName());
    }

    @Override
    public SoapRequestTestStep build() {
        super.build();

        validateNotEmpty(getTestStep().getWsdl(), "Missing WSDL location");
        validateNotEmpty(getTestStep().getBinding(), "Missing WSDL Binding");
        validateNotEmpty(getTestStep().getOperation(), "Missing WSDL Operation");

        getTestStep().setParameters( parameters );

        return getTestStep();
    }

    public SoapRequestStepBuilder withWsdlAt(String wsdlUrl) {
        getTestStep().setWsdl(wsdlUrl);
        return this;
    }

    public SoapRequestStepBuilder forOperation(String operation) {
        getTestStep().setOperation(operation);
        return this;
    }

    public SoapRequestStepBuilder forBinding(String binding) {
        getTestStep().setBinding(binding);
        return this;
    }

    public SoapRequestStepBuilder withEndpoint(String endpoint) {
        getTestStep().setURI(endpoint);
        return this;
    }

    public SoapRequestStepBuilder assertSchemaCompliance() {
        return addAssertion(new SchemaComplianceAssertionBuilder());
    }

    public SoapRequestStepBuilder assertSoapFaultResponse() {
        return addAssertion(new SoapFaultAssertionBuilder());
    }

    public SoapRequestStepBuilder assertSoapOkResponse() {
        return addAssertion(new NotSoapFaultAssertionBuilder());
    }

    public SoapRequestStepBuilder withParameter(String name, String value) {
        SoapParameter parameter = new SoapParameter();
        parameter.setName(name);
        parameter.setValue(value);

        parameters.add(parameter);
        return this;
    }

    public SoapRequestStepBuilder withPathParameter(String path, String value) {
        SoapParameter parameter = new SoapParameter();
        parameter.setPath(path);
        parameter.setValue(value);

        parameters.add(parameter);
        return this;
    }
}
