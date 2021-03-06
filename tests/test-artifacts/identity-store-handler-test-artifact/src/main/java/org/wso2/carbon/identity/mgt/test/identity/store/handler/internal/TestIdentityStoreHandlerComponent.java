/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.mgt.test.identity.store.handler.internal;


import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.identity.event.AbstractEventHandler;
import org.wso2.carbon.identity.event.EventService;
import org.wso2.carbon.identity.mgt.test.identity.store.handler.TestIdentityStoreHandler;

/**
 * OSGi component for TestIdentityStoreHandler.
 */
@Component(
        name = "org.wso2.carbon.identity.mgt.test.identity.store.handler.internal.TestIdentityStoreHandlerComponent",
        immediate = true
)
public class TestIdentityStoreHandlerComponent {

    @Activate
    public void registerIdentityStoreEventListener(BundleContext bundleContext) {
        bundleContext.registerService(AbstractEventHandler.class, new TestIdentityStoreHandler(), null);

    }

    protected void unsetIdentityEventService(EventService eventService) {
        TestIdentityStoreHandlerDataHolder.getInstance().setIdentityEventService(null);
    }

    @Reference(
            name = "EventService",
            service = EventService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetIdentityEventService"
    )
    protected void setIdentityEventService(EventService eventService) {
        TestIdentityStoreHandlerDataHolder.getInstance().setIdentityEventService(eventService);
    }
}
