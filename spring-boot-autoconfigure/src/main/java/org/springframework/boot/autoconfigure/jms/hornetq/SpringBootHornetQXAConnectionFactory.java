/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.jms.hornetq;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.core.client.ServerLocator;
import org.hornetq.jms.client.HornetQXAConnectionFactory;

import org.springframework.util.StringUtils;

/**
 * A {@link HornetQXAConnectionFactory} that manages the credentials of the connection.
 *
 * @author Stéphane Lagraulet
 * @author Stephane Nicoll
 */
class SpringBootHornetQXAConnectionFactory extends HornetQXAConnectionFactory {

	private final HornetQProperties properties;

	SpringBootHornetQXAConnectionFactory(HornetQProperties properties,
			ServerLocator serverLocator) {
		super(serverLocator);
		this.properties = properties;
	}

	SpringBootHornetQXAConnectionFactory(HornetQProperties properties, boolean ha,
			TransportConfiguration... initialConnectors) {
		super(ha, initialConnectors);
		this.properties = properties;
	}

	public Connection createConnection() throws JMSException {
		String user = this.properties.getUser();
		if (StringUtils.hasText(user)) {
			return createConnection(user, this.properties.getPassword());
		}
		return super.createConnection();
	}

}
