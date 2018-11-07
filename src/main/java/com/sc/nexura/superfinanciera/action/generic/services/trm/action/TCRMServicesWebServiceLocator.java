/**
 * TCRMServicesWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sc.nexura.superfinanciera.action.generic.services.trm.action;

public class TCRMServicesWebServiceLocator extends org.apache.axis.client.Service implements com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesWebService {

    public TCRMServicesWebServiceLocator() {
    }


    public TCRMServicesWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TCRMServicesWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TCRMServicesWebServicePort
    private java.lang.String TCRMServicesWebServicePort_address = "http://superfinanciera.gov.co:8080/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService";

    public java.lang.String getTCRMServicesWebServicePortAddress() {
        return TCRMServicesWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TCRMServicesWebServicePortWSDDServiceName = "TCRMServicesWebServicePort";

    public java.lang.String getTCRMServicesWebServicePortWSDDServiceName() {
        return TCRMServicesWebServicePortWSDDServiceName;
    }

    public void setTCRMServicesWebServicePortWSDDServiceName(java.lang.String name) {
        TCRMServicesWebServicePortWSDDServiceName = name;
    }

    public com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterface getTCRMServicesWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TCRMServicesWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTCRMServicesWebServicePort(endpoint);
    }

    public com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterface getTCRMServicesWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesWebServiceSoapBindingStub _stub = new com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getTCRMServicesWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTCRMServicesWebServicePortEndpointAddress(java.lang.String address) {
        TCRMServicesWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesWebServiceSoapBindingStub _stub = new com.sc.nexura.superfinanciera.action.generic.services.trm.action.TCRMServicesWebServiceSoapBindingStub(new java.net.URL(TCRMServicesWebServicePort_address), this);
                _stub.setPortName(getTCRMServicesWebServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TCRMServicesWebServicePort".equals(inputPortName)) {
            return getTCRMServicesWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", "TCRMServicesWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://action.trm.services.generic.action.superfinanciera.nexura.sc.com.co/", "TCRMServicesWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TCRMServicesWebServicePort".equals(portName)) {
            setTCRMServicesWebServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
