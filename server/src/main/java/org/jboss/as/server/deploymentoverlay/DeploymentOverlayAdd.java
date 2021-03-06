/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.server.deploymentoverlay;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DEPLOYMENT_OVERLAY;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;

import org.jboss.as.controller.AbstractAddStepHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.dmr.ModelNode;

/**
 * @author Stuart Douglas
 */
public class DeploymentOverlayAdd extends AbstractAddStepHandler {

    public static final DeploymentOverlayAdd INSTANCE = new DeploymentOverlayAdd();

    private DeploymentOverlayAdd() {
        super(DeploymentOverlayDefinition.attributes());
    }

    @Override
    public void execute(final OperationContext context, final ModelNode operation) throws OperationFailedException {

        //check that if this is a server group level op the referenced deployment overlay exists
        final PathAddress address = PathAddress.pathAddress(operation.get(OP_ADDR));
        if (address.size() > 1) {
            final String name = address.getLastElement().getValue();
            context.readResourceFromRoot(PathAddress.pathAddress(PathElement.pathElement(DEPLOYMENT_OVERLAY, name)));
        }
        super.execute(context, operation);
    }

    @Override
    protected boolean requiresRuntime(OperationContext context) {
        return false;
    }
}
