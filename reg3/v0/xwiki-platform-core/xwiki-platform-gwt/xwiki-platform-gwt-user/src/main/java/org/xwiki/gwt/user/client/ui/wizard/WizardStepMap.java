/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
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
package org.xwiki.gwt.user.client.ui.wizard;

import java.util.HashMap;



/**
 * A generic wizard step provider implemented as a map of wizard steps. The key into the map is the step name.
 * 
 * @version $Id: 3cfd1892e61aacbdc123f3be1c2930e18b253617 $
 */
public class WizardStepMap extends HashMap<String, WizardStep> implements WizardStepProvider
{
    /**
     * Field required by all {@link java.io.Serializable} classes.
     */
    private static final long serialVersionUID = 6713667523066371331L;

    @Override
    public WizardStep getStep(String name)
    {
        return get(name);
    }
}
