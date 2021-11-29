//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.qt.demo.common.mq.java;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.rabbitmq.http.client.HttpClientBuilderConfigurator;
import com.rabbitmq.http.client.domain.AlivenessTestResult;
import com.rabbitmq.http.client.domain.BindingInfo;
import com.rabbitmq.http.client.domain.ChannelInfo;
import com.rabbitmq.http.client.domain.ClusterId;
import com.rabbitmq.http.client.domain.ConnectionInfo;
import com.rabbitmq.http.client.domain.CurrentUserDetails;
import com.rabbitmq.http.client.domain.Definitions;
import com.rabbitmq.http.client.domain.ExchangeInfo;
import com.rabbitmq.http.client.domain.NodeInfo;
import com.rabbitmq.http.client.domain.OverviewResponse;
import com.rabbitmq.http.client.domain.PolicyInfo;
import com.rabbitmq.http.client.domain.QueueInfo;
import com.rabbitmq.http.client.domain.ShovelInfo;
import com.rabbitmq.http.client.domain.ShovelStatus;
import com.rabbitmq.http.client.domain.TopicPermissions;
import com.rabbitmq.http.client.domain.UserInfo;
import com.rabbitmq.http.client.domain.UserPermissions;
import com.rabbitmq.http.client.domain.VhostInfo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

public class MQHttp {
    private static final HttpClientBuilderConfigurator NO_OP_HTTP_CLIENT_BUILDER_CONFIGURATOR = (builder) -> {
        return builder;
    };
    private RestTemplate rt;
    private URI rootUri;

    public MQHttp(String url, String username, String password) throws MalformedURLException, URISyntaxException {
        this(new URL(url), username, password);
    }

    public MQHttp(String url, String username, String password, HttpClientBuilderConfigurator configurator) throws MalformedURLException, URISyntaxException {
        this(new URL(url), username, password, configurator);
    }

    public MQHttp(URL url, String username, String password) throws MalformedURLException, URISyntaxException {
        this(url, username, password, (SSLConnectionSocketFactory) null, (SSLContext) null);
    }

    public MQHttp(URL url, String username, String password, HttpClientBuilderConfigurator configurator) throws MalformedURLException, URISyntaxException {
        this(url, username, password, (SSLConnectionSocketFactory) null, (SSLContext) null, configurator);
    }

    private MQHttp(URL url, String username, String password, SSLConnectionSocketFactory sslConnectionSocketFactory, SSLContext sslContext) throws MalformedURLException, URISyntaxException {
        Assert.notNull(url, "URL is required; it must not be null");
        Assert.notNull(username, "username is required; it must not be null");
        Assert.notNull(password, "password is required; it must not be null");
        if (url.toString().endsWith("/")) {
            this.rootUri = url.toURI();
        } else {
            this.rootUri = (new URL(url.toString() + "/")).toURI();
        }

        this.rt = new RestTemplate(this.getRequestFactory(url, username, password, sslConnectionSocketFactory, sslContext, NO_OP_HTTP_CLIENT_BUILDER_CONFIGURATOR));
        this.rt.setMessageConverters(this.getMessageConverters());
    }

    public MQHttp(URL url, String username, String password, SSLContext sslContext) throws MalformedURLException, URISyntaxException {
        this(url, username, password, (SSLConnectionSocketFactory) null, sslContext);
    }

    private MQHttp(URL url, String username, String password, SSLConnectionSocketFactory sslConnectionSocketFactory) throws MalformedURLException, URISyntaxException {
        this(url, username, password, sslConnectionSocketFactory, (SSLContext) null);
    }

    public MQHttp(String url) throws MalformedURLException, URISyntaxException {
        this(urlWithoutCredentials(url), StringUtils.split((new URL(url)).getUserInfo(), ":")[0], StringUtils.split((new URL(url)).getUserInfo(), ":")[1]);
    }

    public MQHttp(URL url) throws MalformedURLException, URISyntaxException {
        this((URL) url, (String) null, (String) null);
    }

    private MQHttp(URL url, String username, String password, SSLConnectionSocketFactory sslConnectionSocketFactory, SSLContext sslContext, HttpClientBuilderConfigurator configurator) throws URISyntaxException, MalformedURLException {
        Assert.notNull(url, "URL is required; it must not be null");
        Assert.notNull(username, "username is required; it must not be null");
        Assert.notNull(password, "password is required; it must not be null");
        Assert.notNull(configurator, "configurator is required; it must not be null");
        this.rootUri = url.toURI();
        HttpComponentsClientHttpRequestFactory rf = this.getRequestFactory(url, username, password, sslConnectionSocketFactory, sslContext, configurator);
        this.rt = new RestTemplate(rf);
        this.rt.setMessageConverters(this.getMessageConverters());
    }

    public OverviewResponse getOverview() {
        return (OverviewResponse) this.rt.getForObject(this.uriWithPath("./overview"), OverviewResponse.class);
    }

    public boolean alivenessTest(String vhost) {
        URI uri = this.uriWithPath("./aliveness-test/" + this.encodePathSegment(vhost));
        return ((AlivenessTestResult) this.rt.getForObject(uri, AlivenessTestResult.class)).isSuccessful();
    }

    public CurrentUserDetails whoAmI() {
        URI uri = this.uriWithPath("./whoami/");
        return (CurrentUserDetails) this.rt.getForObject(uri, CurrentUserDetails.class);
    }

    public List<NodeInfo> getNodes() {
        URI uri = this.uriWithPath("./nodes/");
        return Arrays.asList((NodeInfo[]) this.rt.getForObject(uri, NodeInfo[].class));
    }

    public NodeInfo getNode(String name) {
        URI uri = this.uriWithPath("./nodes/" + this.encodePathSegment(name));
        return (NodeInfo) this.rt.getForObject(uri, NodeInfo.class);
    }

    public List<ConnectionInfo> getConnections() {
        URI uri = this.uriWithPath("./connections/");
        return Arrays.asList((ConnectionInfo[]) this.rt.getForObject(uri, ConnectionInfo[].class));
    }

    public ConnectionInfo getConnection(String name) {
        URI uri = this.uriWithPath("./connections/" + this.encodePathSegment(name));
        return (ConnectionInfo) this.rt.getForObject(uri, ConnectionInfo.class);
    }

    public void closeConnection(String name) {
        URI uri = this.uriWithPath("./connections/" + this.encodePathSegment(name));
        this.deleteIgnoring404(uri);
    }

    public void closeConnection(String name, String reason) {
        URI uri = this.uriWithPath("./connections/" + this.encodePathSegment(name));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap();
        headers.put("X-Reason", Collections.singletonList(reason));
        this.deleteIgnoring404(uri, headers);
    }

    public List<ChannelInfo> getChannels() {
        URI uri = this.uriWithPath("./channels/");
        return Arrays.asList((ChannelInfo[]) this.rt.getForObject(uri, ChannelInfo[].class));
    }

    public List<ChannelInfo> getChannels(String connectionName) {
        URI uri = this.uriWithPath("./connections/" + this.encodePathSegment(connectionName) + "/channels/");
        return Arrays.asList((ChannelInfo[]) this.rt.getForObject(uri, ChannelInfo[].class));
    }

    public ChannelInfo getChannel(String name) {
        URI uri = this.uriWithPath("./channels/" + this.encodePathSegment(name));
        return (ChannelInfo) this.rt.getForObject(uri, ChannelInfo.class);
    }

    public List<VhostInfo> getVhosts() {
        URI uri = this.uriWithPath("./vhosts/");
        return Arrays.asList((VhostInfo[]) this.rt.getForObject(uri, VhostInfo[].class));
    }

    public VhostInfo getVhost(String name) {
        URI uri = this.uriWithPath("./vhosts/" + this.encodePathSegment(name));
        return (VhostInfo) this.getForObjectReturningNullOn404(uri, VhostInfo.class);
    }

    public void createVhost(String name) throws JsonProcessingException {
        URI uri = this.uriWithPath("./vhosts/" + this.encodePathSegment(name));
        this.rt.put(uri, (Object) null);
    }

    public void deleteVhost(String name) {
        URI uri = this.uriWithPath("./vhosts/" + this.encodePathSegment(name));
        this.deleteIgnoring404(uri);
    }

    public List<UserPermissions> getPermissionsIn(String vhost) {
        URI uri = this.uriWithPath("./vhosts/" + this.encodePathSegment(vhost) + "/permissions");
        UserPermissions[] result = (UserPermissions[]) this.getForObjectReturningNullOn404(uri, UserPermissions[].class);
        return this.asListOrNull(result);
    }

    public List<UserPermissions> getPermissionsOf(String username) {
        URI uri = this.uriWithPath("./users/" + this.encodePathSegment(username) + "/permissions");
        UserPermissions[] result = (UserPermissions[]) this.getForObjectReturningNullOn404(uri, UserPermissions[].class);
        return this.asListOrNull(result);
    }

    public List<UserPermissions> getPermissions() {
        URI uri = this.uriWithPath("./permissions");
        UserPermissions[] result = (UserPermissions[]) this.getForObjectReturningNullOn404(uri, UserPermissions[].class);
        return this.asListOrNull(result);
    }

    public UserPermissions getPermissions(String vhost, String username) {
        URI uri = this.uriWithPath("./permissions/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(username));
        return (UserPermissions) this.getForObjectReturningNullOn404(uri, UserPermissions.class);
    }

    public List<TopicPermissions> getTopicPermissionsIn(String vhost) {
        URI uri = this.uriWithPath("./vhosts/" + this.encodePathSegment(vhost) + "/topic-permissions");
        TopicPermissions[] result = (TopicPermissions[]) this.getForObjectReturningNullOn404(uri, TopicPermissions[].class);
        return this.asListOrNull(result);
    }

    public List<TopicPermissions> getTopicPermissionsOf(String username) {
        URI uri = this.uriWithPath("./users/" + this.encodePathSegment(username) + "/topic-permissions");
        TopicPermissions[] result = (TopicPermissions[]) this.getForObjectReturningNullOn404(uri, TopicPermissions[].class);
        return this.asListOrNull(result);
    }

    public List<TopicPermissions> getTopicPermissions() {
        URI uri = this.uriWithPath("./topic-permissions");
        TopicPermissions[] result = (TopicPermissions[]) this.getForObjectReturningNullOn404(uri, TopicPermissions[].class);
        return this.asListOrNull(result);
    }

    public List<TopicPermissions> getTopicPermissions(String vhost, String username) {
        URI uri = this.uriWithPath("./topic-permissions/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(username));
        return this.asListOrNull((TopicPermissions[]) this.getForObjectReturningNullOn404(uri, TopicPermissions[].class));
    }

    public List<ExchangeInfo> getExchanges() {
        URI uri = this.uriWithPath("./exchanges/");
        return Arrays.asList((ExchangeInfo[]) this.rt.getForObject(uri, ExchangeInfo[].class));
    }

    public List<ExchangeInfo> getExchanges(String vhost) {
        URI uri = this.uriWithPath("./exchanges/" + this.encodePathSegment(vhost));
        ExchangeInfo[] result = (ExchangeInfo[]) this.getForObjectReturningNullOn404(uri, ExchangeInfo[].class);
        return this.asListOrNull(result);
    }

    public ExchangeInfo getExchange(String vhost, String name) {
        URI uri = this.uriWithPath("./exchanges/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name));
        return (ExchangeInfo) this.getForObjectReturningNullOn404(uri, ExchangeInfo.class);
    }

    public void declareExchange(String vhost, String name, ExchangeInfo info) {
        URI uri = this.uriWithPath("./exchanges/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name));
        this.rt.put(uri, info);
    }

    public void deleteExchange(String vhost, String name) {
        this.deleteIgnoring404(this.uriWithPath("./exchanges/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name)));
    }

    public List<QueueInfo> getQueues() {
        URI uri = this.uriWithPath("./queues/");
        return Arrays.asList((QueueInfo[]) this.rt.getForObject(uri, QueueInfo[].class));
    }

    public List<QueueInfo> getQueues(String vhost) {
        URI uri = this.uriWithPath("./queues/" + this.encodePathSegment(vhost));
        QueueInfo[] result = (QueueInfo[]) this.getForObjectReturningNullOn404(uri, QueueInfo[].class);
        return this.asListOrNull(result);
    }

    public QueueInfo getQueue(String vhost, String name) {
        URI uri = this.uriWithPath("./queues/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name));
        return (QueueInfo) this.getForObjectReturningNullOn404(uri, QueueInfo.class);
    }

    public void declarePolicy(String vhost, String name, PolicyInfo info) {
        URI uri = this.uriWithPath("./policies/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name));
        this.rt.put(uri, info);
    }

    public void declareQueue(String vhost, String name, QueueInfo info) {
        URI uri = this.uriWithPath("./queues/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name));
        this.rt.put(uri, info);
    }

    public void purgeQueue(String vhost, String name) {
        this.deleteIgnoring404(this.uriWithPath("./queues/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name) + "/contents/"));
    }

    public void deleteQueue(String vhost, String name) {
        this.deleteIgnoring404(this.uriWithPath("./queues/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name)));
    }

    public void deletePolicy(String vhost, String name) {
        this.deleteIgnoring404(this.uriWithPath("./policies/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(name)));
    }

    public List<UserInfo> getUsers() {
        URI uri = this.uriWithPath("./users/");
        return Arrays.asList((UserInfo[]) this.rt.getForObject(uri, UserInfo[].class));
    }

    public UserInfo getUser(String username) {
        URI uri = this.uriWithPath("./users/" + this.encodePathSegment(username));
        return (UserInfo) this.getForObjectReturningNullOn404(uri, UserInfo.class);
    }

    public void createUser(String username, char[] password, List<String> tags) {
        if (username == null) {
            throw new IllegalArgumentException("username cannot be null");
        } else if (password == null) {
            throw new IllegalArgumentException("password cannot be null or empty. If you need to create a user that will only authenticate using an x509 certificate, use createUserWithPasswordHash with a blank hash.");
        } else {
            Map<String, Object> body = new HashMap();
            body.put("password", new String(password));
            body.put("tags", this.joinStrings(",", tags));
            URI uri = this.uriWithPath("./users/" + this.encodePathSegment(username));
            this.rt.put(uri, body);
        }
    }

    public void createUserWithPasswordHash(String username, char[] passwordHash, List<String> tags) {
        if (username == null) {
            throw new IllegalArgumentException("username cannot be null");
        } else {
            if (passwordHash == null) {
                passwordHash = "".toCharArray();
            }

            Map<String, Object> body = new HashMap();
            body.put("password_hash", String.valueOf(passwordHash));
            body.put("tags", this.joinStrings(",", tags));
            URI uri = this.uriWithPath("./users/" + this.encodePathSegment(username));
            this.rt.put(uri, body);
        }
    }

    public void updateUser(String username, char[] password, List<String> tags) {
        if (username == null) {
            throw new IllegalArgumentException("username cannot be null");
        } else {
            Map<String, Object> body = new HashMap();
            if (password != null) {
                body.put("password", new String(password));
            }

            body.put("tags", this.joinStrings(",", tags));
            URI uri = this.uriWithPath("./users/" + this.encodePathSegment(username));
            this.rt.put(uri, body);
        }
    }

    public void deleteUser(String username) {
        this.deleteIgnoring404(this.uriWithPath("./users/" + this.encodePathSegment(username)));
    }

    public void updatePermissions(String vhost, String username, UserPermissions permissions) {
        URI uri = this.uriWithPath("./permissions/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(username));
        this.rt.put(uri, permissions);
    }

    public void clearPermissions(String vhost, String username) {
        URI uri = this.uriWithPath("./permissions/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(username));
        this.deleteIgnoring404(uri);
    }

    public void updateTopicPermissions(String vhost, String username, TopicPermissions permissions) {
        URI uri = this.uriWithPath("./topic-permissions/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(username));
        this.rt.put(uri, permissions);
    }

    public void clearTopicPermissions(String vhost, String username) {
        URI uri = this.uriWithPath("./topic-permissions/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(username));
        this.deleteIgnoring404(uri);
    }

    public List<PolicyInfo> getPolicies() {
        URI uri = this.uriWithPath("./policies/");
        return Arrays.asList((PolicyInfo[]) this.rt.getForObject(uri, PolicyInfo[].class));
    }

    public List<PolicyInfo> getPolicies(String vhost) {
        URI uri = this.uriWithPath("./policies/" + this.encodePathSegment(vhost));
        PolicyInfo[] result = (PolicyInfo[]) this.getForObjectReturningNullOn404(uri, PolicyInfo[].class);
        return this.asListOrNull(result);
    }

    public List<BindingInfo> getBindings() {
        URI uri = this.uriWithPath("./bindings/");
        return Arrays.asList((BindingInfo[]) this.rt.getForObject(uri, BindingInfo[].class));
    }

    public List<BindingInfo> getBindings(String vhost) {
        URI uri = this.uriWithPath("./bindings/" + this.encodePathSegment(vhost));
        return Arrays.asList((BindingInfo[]) this.rt.getForObject(uri, BindingInfo[].class));
    }

    public List<BindingInfo> getBindingsBySource(String vhost, String exchange) {
        String x = exchange.equals("") ? "amq.default" : exchange;
        URI uri = this.uriWithPath("./exchanges/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(x) + "/bindings/source");
        return Arrays.asList((BindingInfo[]) this.rt.getForObject(uri, BindingInfo[].class));
    }

    public List<BindingInfo> getExchangeBindingsByDestination(String vhost, String exchange) {
        String x = exchange.equals("") ? "amq.default" : exchange;
        URI uri = this.uriWithPath("./exchanges/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(x) + "/bindings/destination");
        BindingInfo[] result = (BindingInfo[]) this.rt.getForObject(uri, BindingInfo[].class);
        return this.asListOrNull(result);
    }

    public List<BindingInfo> getQueueBindings(String vhost, String queue) {
        URI uri = this.uriWithPath("./queues/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(queue) + "/bindings");
        BindingInfo[] result = (BindingInfo[]) this.rt.getForObject(uri, BindingInfo[].class);
        return this.asListOrNull(result);
    }

    public List<BindingInfo> getQueueBindingsBetween(String vhost, String exchange, String queue) {
        URI uri = this.uriWithPath("./bindings/" + this.encodePathSegment(vhost) + "/e/" + this.encodePathSegment(exchange) + "/q/" + this.encodePathSegment(queue));
        BindingInfo[] result = (BindingInfo[]) this.rt.getForObject(uri, BindingInfo[].class);
        return this.asListOrNull(result);
    }

    public List<BindingInfo> getExchangeBindingsBetween(String vhost, String source, String destination) {
        URI uri = this.uriWithPath("./bindings/" + this.encodePathSegment(vhost) + "/e/" + this.encodePathSegment(source) + "/e/" + this.encodePathSegment(destination));
        BindingInfo[] result = (BindingInfo[]) this.rt.getForObject(uri, BindingInfo[].class);
        return this.asListOrNull(result);
    }

    public void bindQueue(String vhost, String queue, String exchange, String routingKey) {
        this.bindQueue(vhost, queue, exchange, routingKey, new HashMap());
    }

    public void bindQueue(String vhost, String queue, String exchange, String routingKey, Map<String, Object> args) {
        if (vhost != null && !vhost.isEmpty()) {
            if (queue != null && !queue.isEmpty()) {
                if (exchange != null && !exchange.isEmpty()) {
                    Map<String, Object> body = new HashMap();
                    if (args != null) {
                        body.put("arguments", args);
                    }

                    body.put("routing_key", routingKey);
                    URI uri = this.uriWithPath("./bindings/" + this.encodePathSegment(vhost) + "/e/" + this.encodePathSegment(exchange) + "/q/" + this.encodePathSegment(queue));
                    this.rt.postForLocation(uri, body);
                } else {
                    throw new IllegalArgumentException("exchange cannot be null or blank");
                }
            } else {
                throw new IllegalArgumentException("queue cannot be null or blank");
            }
        } else {
            throw new IllegalArgumentException("vhost cannot be null or blank");
        }
    }

    public void unbindQueue(String vhost, String queue, String exchange, String routingKey) {
        if (vhost != null && !vhost.isEmpty()) {
            if (queue != null && !queue.isEmpty()) {
                if (exchange != null && !exchange.isEmpty()) {
                    this.deleteIgnoring404(this.uriWithPath("./bindings/" + this.encodePathSegment(vhost) + "/e/" + this.encodePathSegment(exchange) + "/q/" + this.encodePathSegment(queue) + '/' + this.encodePathSegment(routingKey)));
                } else {
                    throw new IllegalArgumentException("exchange cannot be null or blank");
                }
            } else {
                throw new IllegalArgumentException("queue cannot be null or blank");
            }
        } else {
            throw new IllegalArgumentException("vhost cannot be null or blank");
        }
    }

    public void bindExchange(String vhost, String destination, String source, String routingKey) {
        this.bindExchange(vhost, destination, source, routingKey, new HashMap());
    }

    public void bindExchange(String vhost, String destination, String source, String routingKey, Map<String, Object> args) {
        if (vhost != null && !vhost.isEmpty()) {
            if (destination != null && !destination.isEmpty()) {
                if (source != null && !source.isEmpty()) {
                    Map<String, Object> body = new HashMap();
                    if (args != null) {
                        body.put("arguments", args);
                    }

                    body.put("routing_key", routingKey);
                    URI uri = this.uriWithPath("./bindings/" + this.encodePathSegment(vhost) + "/e/" + this.encodePathSegment(source) + "/e/" + this.encodePathSegment(destination));
                    this.rt.postForLocation(uri, body);
                } else {
                    throw new IllegalArgumentException("source cannot be null or blank");
                }
            } else {
                throw new IllegalArgumentException("destination cannot be null or blank");
            }
        } else {
            throw new IllegalArgumentException("vhost cannot be null or blank");
        }
    }

    public void unbindExchange(String vhost, String destination, String source, String routingKey) {
        if (vhost != null && !vhost.isEmpty()) {
            if (destination != null && !destination.isEmpty()) {
                if (source != null && !source.isEmpty()) {
                    this.deleteIgnoring404(this.uriWithPath("./bindings/" + this.encodePathSegment(vhost) + "/e/" + this.encodePathSegment(source) + "/e/" + this.encodePathSegment(destination) + '/' + this.encodePathSegment(routingKey)));
                } else {
                    throw new IllegalArgumentException("source cannot be null or blank");
                }
            } else {
                throw new IllegalArgumentException("destination cannot be null or blank");
            }
        } else {
            throw new IllegalArgumentException("vhost cannot be null or blank");
        }
    }

    public ClusterId getClusterName() {
        return (ClusterId) this.rt.getForObject(this.uriWithPath("./cluster-name"), ClusterId.class);
    }

    public void setClusterName(String name) {
        if (name != null && !name.isEmpty()) {
            URI uri = this.uriWithPath("./cluster-name");
            Map<String, String> m = new HashMap();
            m.put("name", name);
            this.rt.put(uri, m);
        } else {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
    }

    public List<Map> getExtensions() {
        URI uri = this.uriWithPath("./extensions/");
        return Arrays.asList((Map[]) this.rt.getForObject(uri, Map[].class));
    }

    public Definitions getDefinitions() {
        URI uri = this.uriWithPath("./definitions/");
        return (Definitions) this.rt.getForObject(uri, Definitions.class);
    }

    public void declareShovel(String vhost, ShovelInfo info) {
        Map<String, Object> props = info.getDetails().getPublishProperties();
        if (props != null && props.isEmpty()) {
            throw new IllegalArgumentException("Shovel publish properties must be a non-empty map or null");
        } else {
            URI uri = this.uriWithPath("./parameters/shovel/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(info.getName()));
            this.rt.put(uri, info);
        }
    }

    public List<ShovelInfo> getShovels() {
        URI uri = this.uriWithPath("./parameters/shovel/");
        return Arrays.asList((ShovelInfo[]) this.rt.getForObject(uri, ShovelInfo[].class));
    }

    public List<ShovelInfo> getShovels(String vhost) {
        URI uri = this.uriWithPath("./parameters/shovel/" + this.encodePathSegment(vhost));
        ShovelInfo[] result = (ShovelInfo[]) this.getForObjectReturningNullOn404(uri, ShovelInfo[].class);
        return this.asListOrNull(result);
    }

    public List<ShovelStatus> getShovelsStatus() {
        URI uri = this.uriWithPath("./shovels/");
        return Arrays.asList((ShovelStatus[]) this.rt.getForObject(uri, ShovelStatus[].class));
    }

    public List<ShovelStatus> getShovelsStatus(String vhost) {
        URI uri = this.uriWithPath("./shovels/" + this.encodePathSegment(vhost));
        ShovelStatus[] result = (ShovelStatus[]) this.getForObjectReturningNullOn404(uri, ShovelStatus[].class);
        return this.asListOrNull(result);
    }

    public void deleteShovel(String vhost, String shovelname) {
        this.deleteIgnoring404(this.uriWithPath("./parameters/shovel/" + this.encodePathSegment(vhost) + "/" + this.encodePathSegment(shovelname)));
    }

    private URI uriWithPath(String path) {
        return this.rootUri.resolve(path);
    }

    private String encodePathSegment(String pathSegment) {
        return UriUtils.encodePathSegment(pathSegment, "UTF-8");
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> xs = new ArrayList();
        Jackson2ObjectMapperBuilder bldr = Jackson2ObjectMapperBuilder.json().serializationInclusion(Include.NON_NULL).featuresToEnable(new Object[]{DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT});
        xs.add(new MappingJackson2HttpMessageConverter(bldr.build()));
        return xs;
    }

    private HttpComponentsClientHttpRequestFactory getRequestFactory(URL url, String username, String password, SSLConnectionSocketFactory sslConnectionSocketFactory, SSLContext sslContext, HttpClientBuilderConfigurator configurator) throws MalformedURLException {
        String theUser = username;
        String thePassword = password;
        String userInfo = url.getUserInfo();
        if (userInfo != null && username == null) {
            String[] userParts = userInfo.split(":");
            if (userParts.length > 0) {
                theUser = userParts[0];
            }

            if (userParts.length > 1) {
                thePassword = userParts[1];
            }
        }

        HttpClientBuilder bldr = HttpClientBuilder.create().setDefaultCredentialsProvider(this.getCredentialsProvider(url, theUser, thePassword));
        if (sslConnectionSocketFactory != null) {
            bldr.setSSLSocketFactory(sslConnectionSocketFactory);
        }

        if (sslContext != null) {
            bldr.setSSLContext(sslContext);
        }

        HttpClientBuilder b = configurator.configure(bldr);
        HttpClient httpClient = b.build();
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicScheme = new BasicScheme();
        authCache.put(new HttpHost(this.rootUri.getHost(), this.rootUri.getPort(), this.rootUri.getScheme()), basicScheme);
        final HttpClientContext ctx = HttpClientContext.create();
        ctx.setAuthCache(authCache);
        return new HttpComponentsClientHttpRequestFactory(httpClient) {
            protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
                return ctx;
            }
        };
    }

    private CredentialsProvider getCredentialsProvider(URL url, String username, String password) {
        CredentialsProvider cp = new BasicCredentialsProvider();
        cp.setCredentials(new AuthScope(AuthScope.ANY_HOST, -1), new UsernamePasswordCredentials(username, password));
        return cp;
    }

    private <T> T getForObjectReturningNullOn404(URI uri, Class<T> klass) {
        try {
            return this.rt.getForObject(uri, klass);
        } catch (HttpClientErrorException var4) {
            if (var4.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
                throw var4;
            }
        }
    }

    private void deleteIgnoring404(URI uri) {
        try {
            this.rt.delete(uri);
        } catch (HttpClientErrorException var3) {
            if (var3.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw var3;
            }
        }

    }

    private void deleteIgnoring404(URI uri, MultiValueMap<String, String> headers) {
        try {
            HttpEntity<Object> entity = new HttpEntity((Object) null, headers);
            this.rt.exchange(uri, HttpMethod.DELETE, entity, Object.class);
        } catch (HttpClientErrorException var4) {
            if (var4.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw var4;
            }
        }

    }

    private <T> List<T> asListOrNull(T[] result) {
        return result == null ? null : Arrays.asList(result);
    }

    private String joinStrings(String delimiter, List<String> tags) {
        StringBuilder sb = new StringBuilder();
        boolean appendedFirst = false;
        Iterator var5 = tags.iterator();

        while (var5.hasNext()) {
            String tag = (String) var5.next();
            if (!appendedFirst) {
                sb.append(tag);
                appendedFirst = true;
            } else {
                sb.append(delimiter);
                if (tag != null) {
                    sb.append(tag);
                }
            }
        }

        return sb.toString();
    }

    private static String urlWithoutCredentials(String url) throws MalformedURLException {
        URL url1 = new URL(url);
        return StringUtils.replace(url, url1.getUserInfo() + "@", "");
    }

    /**
     * 查询所有queue消息信息
     * @param queuename
     * @param count
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<String> getMessageAllQueueList(String queuename, long count) throws IOException, ClassNotFoundException {
        final URI uri = uriWithPath("./queues");
        ResponseEntity<List> result = rt.getForEntity(uri, List.class);
        List<String> resultList = new ArrayList<>();
        List list = result.getBody();
        for (Object entry : list) {
            if (entry instanceof Map) {
//                String base64Str = ((Map) entry).get("payload") == null ? "" : ((Map) entry).get("payload").toString();
//                byte[] bytes = Base64.decodeBase64(base64Str);

//                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//                ObjectInputStream ois = new ObjectInputStream(bais);
//                String dto = (String) ois.readObject();
//                resultList.add(new String(bytes));
            }
        }
        return resultList;

    }

    /**
     * 查询vhost下的对应queue的消息详情
     * @param vhost
     * @param queuename
     * @param count
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<String> getMessageByVhostList(String vhost, String queuename, long count) throws IOException, ClassNotFoundException {
        String bodyStr = "" +
                "{\n" +
                "\t\"count\": \""+count+"\",\n" +
                "\t\"ackmode\": \"ack_requeue_true\",\n" +
                "\t\"encoding\": \"base64\"\n" +
                "}";
        Map<String, String> body = JSONUtil.toBean(bodyStr, Map.class);
        final URI uri = uriWithPath("./queues/" + encodePathSegment(vhost) + "/" + encodePathSegment(queuename) + "/get");
        ResponseEntity<List> result = rt.postForEntity(uri, "", List.class);
        List<String> resultList = new ArrayList<>();
        List list = result.getBody();
        for (Object entry : list) {
            if (entry instanceof Map) {
                String base64Str = ((Map) entry).get("payload") == null ? "" : ((Map) entry).get("payload").toString();
                byte[] bytes = Base64.decodeBase64(base64Str);
                resultList.add(new String(bytes));
            }
        }
        return resultList;

    }
}
