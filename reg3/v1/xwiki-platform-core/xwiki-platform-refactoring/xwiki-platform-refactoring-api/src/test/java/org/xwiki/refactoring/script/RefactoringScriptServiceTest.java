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
package org.xwiki.refactoring.script;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.context.Execution;
import org.xwiki.context.ExecutionContext;
import org.xwiki.job.Job;
import org.xwiki.job.JobException;
import org.xwiki.job.JobExecutor;
import org.xwiki.job.script.JobScriptService;
import org.xwiki.model.EntityType;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.EntityReferenceProvider;
import org.xwiki.model.reference.SpaceReference;
import org.xwiki.model.reference.WikiReference;
import org.xwiki.refactoring.job.CreateRequest;
import org.xwiki.refactoring.job.EntityRequest;
import org.xwiki.refactoring.job.MoveRequest;
import org.xwiki.refactoring.job.RefactoringJobs;
import org.xwiki.script.service.ScriptService;
import org.xwiki.security.authorization.ContextualAuthorizationManager;
import org.xwiki.security.authorization.Right;
import org.xwiki.test.annotation.BeforeComponent;
import org.xwiki.test.mockito.MockitoComponentMockingRule;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RefactoringScriptService}.
 * 
 * @version $Id: 3f47b0f7fe6aedecd6fd4188e5a871a5ccb7591b $
 */
public class RefactoringScriptServiceTest
{
    @Rule
    public MockitoComponentMockingRule<ScriptService> mocker = new MockitoComponentMockingRule<ScriptService>(
        RefactoringScriptService.class);

    private JobExecutor jobExecutor;

    private DocumentReference userReference = new DocumentReference("wiki", "Users", "Carol");

    private ExecutionContext executionContext = new ExecutionContext();

    private JobScriptService jobScriptService = mock(JobScriptService.class);

    @BeforeComponent
    public void initializeComponents() throws Exception
    {
        this.mocker.registerComponent(ScriptService.class, "job", jobScriptService);
    }

    @Before
    public void configure() throws Exception
    {
        this.jobExecutor = this.mocker.getInstance(JobExecutor.class);

        Execution execution = this.mocker.getInstance(Execution.class);
        when(execution.getContext()).thenReturn(executionContext);

        DocumentAccessBridge documentAccessBridge = this.mocker.getInstance(DocumentAccessBridge.class);
        when(documentAccessBridge.getCurrentUserReference()).thenReturn(this.userReference);

        EntityReferenceProvider defaultEntityReferenceProvider = this.mocker.getInstance(EntityReferenceProvider.class);
        when(defaultEntityReferenceProvider.getDefaultReference(EntityType.DOCUMENT)).thenReturn(
            new EntityReference("WebHome", EntityType.DOCUMENT, null));
    }

    @Test
    public void move() throws Exception
    {
        SpaceReference source = new SpaceReference("Space", new WikiReference("math"));
        WikiReference destination = new WikiReference("code");

        Job job = mock(Job.class);
        ArgumentCaptor<MoveRequest> request = ArgumentCaptor.forClass(MoveRequest.class);
        when(this.jobExecutor.execute(eq(RefactoringJobs.MOVE), request.capture())).thenReturn(job);

        assertSame(job, getService().move(source, destination));

        assertEquals(Arrays.asList(source), request.getValue().getEntityReferences());
        assertEquals(destination, request.getValue().getDestination());
        assertEquals(Arrays.asList(RefactoringJobs.GROUP, "move"), request.getValue().getId().subList(0, 2));
        assertEquals(RefactoringJobs.MOVE, request.getValue().getJobType());
        assertEquals(this.userReference, request.getValue().getUserReference());
        assertEquals(false, request.getValue().isDeep());
        assertEquals(true, request.getValue().isDeleteSource());
        assertEquals(true, request.getValue().isUpdateLinks());
        assertEquals(true, request.getValue().isAutoRedirect());
        assertEquals(false, request.getValue().isInteractive());
        assertEquals(true, request.getValue().isCheckRights());
    }

    @Test
    public void moveWithoutPR() throws Exception
    {
        MoveRequest request = new MoveRequest();
        request.setCheckRights(false);
        request.setUserReference(new DocumentReference("wiki", "Users", "Bob"));

        getService().move(request);

        assertTrue(request.isCheckRights());
        assertEquals(this.userReference, request.getUserReference());
    }

    @Test
    public void moveWithPR() throws Exception
    {
        MoveRequest request = new MoveRequest();
        request.setCheckRights(false);

        DocumentReference bobReference = new DocumentReference("wiki", "Users", "Bob");
        request.setUserReference(bobReference);

        ContextualAuthorizationManager authorization = this.mocker.getInstance(ContextualAuthorizationManager.class);
        when(authorization.hasAccess(Right.PROGRAM)).thenReturn(true);

        getService().move(request);

        assertFalse(request.isCheckRights());
        assertEquals(bobReference, request.getUserReference());
    }

    @Test
    public void moveWithException() throws Exception
    {
        MoveRequest request = new MoveRequest();
        JobException exception = new JobException("Some error message");
        when(this.jobExecutor.execute(RefactoringJobs.MOVE, request)).thenThrow(exception);

        assertNull(getService().move(request));
        assertSame(exception, getService().getLastError());
    }

    @Test
    public void rename() throws Exception
    {
        SpaceReference spaceReference =
            new SpaceReference("Alice", new SpaceReference("Users", new WikiReference("dev")));
        getService().rename(spaceReference, "Bob");

        ArgumentCaptor<MoveRequest> request = ArgumentCaptor.forClass(MoveRequest.class);
        verify(this.jobExecutor).execute(eq(RefactoringJobs.RENAME), request.capture());

        assertEquals(RefactoringJobs.RENAME, request.getValue().getJobType());
        assertEquals(Arrays.asList(spaceReference), request.getValue().getEntityReferences());
        assertEquals(new SpaceReference("Bob", spaceReference.getParent()), request.getValue().getDestination());
    }

    @Test
    public void copy() throws Exception
    {
        SpaceReference source = new SpaceReference("Space", new WikiReference("math"));
        WikiReference destination = new WikiReference("code");

        getService().copy(source, destination);

        ArgumentCaptor<MoveRequest> request = ArgumentCaptor.forClass(MoveRequest.class);
        // The MOVE job can perform a COPY too.
        verify(this.jobExecutor).execute(eq(RefactoringJobs.MOVE), request.capture());

        assertEquals(RefactoringJobs.COPY, request.getValue().getJobType());
        assertFalse(request.getValue().isDeleteSource());
    }

    @Test
    public void copyAs() throws Exception
    {
        SpaceReference spaceReference =
            new SpaceReference("Alice", new SpaceReference("Users", new WikiReference("dev")));
        getService().copyAs(spaceReference, "Bob");

        ArgumentCaptor<MoveRequest> request = ArgumentCaptor.forClass(MoveRequest.class);
        // The RENAME job can perform a COPY too.
        verify(this.jobExecutor).execute(eq(RefactoringJobs.RENAME), request.capture());

        assertEquals(RefactoringJobs.COPY_AS, request.getValue().getJobType());
        assertEquals(Arrays.asList(spaceReference), request.getValue().getEntityReferences());
        assertEquals(new SpaceReference("Bob", spaceReference.getParent()), request.getValue().getDestination());
        assertFalse(request.getValue().isDeleteSource());
    }

    @Test
    public void delete() throws Exception
    {
        WikiReference source = new WikiReference("math");

        getService().delete(source);

        ArgumentCaptor<EntityRequest> request = ArgumentCaptor.forClass(EntityRequest.class);
        verify(this.jobExecutor).execute(eq(RefactoringJobs.DELETE), request.capture());

        assertEquals(RefactoringJobs.DELETE, request.getValue().getJobType());
        assertEquals(Arrays.asList(source), request.getValue().getEntityReferences());
        assertFalse(request.getValue().isDeep());
    }

    @Test
    public void create() throws Exception
    {
        DocumentReference documentReference = new DocumentReference("wiki", "Space", "Page");

        getService().create(documentReference);

        ArgumentCaptor<CreateRequest> request = ArgumentCaptor.forClass(CreateRequest.class);
        verify(this.jobExecutor).execute(eq(RefactoringJobs.CREATE), request.capture());

        assertEquals(RefactoringJobs.CREATE, request.getValue().getJobType());
        assertEquals(Arrays.asList(documentReference), request.getValue().getEntityReferences());
        assertTrue(request.getValue().isDeep());
    }

    @Test
    public void convertToNestedDocument() throws Exception
    {
        DocumentReference terminalDocumentReference = new DocumentReference("code", "Model", "Entity");
        DocumentReference nestedDocumentReference =
            new DocumentReference("code", Arrays.asList("Model", "Entity"), "WebHome");

        getService().convertToNestedDocument(terminalDocumentReference);

        ArgumentCaptor<MoveRequest> request = ArgumentCaptor.forClass(MoveRequest.class);
        verify(this.jobExecutor).execute(eq(RefactoringJobs.RENAME), request.capture());
        assertEquals(Arrays.asList(terminalDocumentReference), request.getValue().getEntityReferences());
        assertEquals(nestedDocumentReference, request.getValue().getDestination());

        assertNull(getService().convertToNestedDocument(nestedDocumentReference));
    }

    @Test
    public void convertToTerminalDocument() throws Exception
    {
        DocumentReference terminalDocumentReference = new DocumentReference("code", "Model", "Entity");
        DocumentReference nestedDocumentReference =
            new DocumentReference("code", Arrays.asList("Model", "Entity"), "WebHome");
        DocumentReference rootDocumentReference = new DocumentReference("wiki", "Space", "WebHome");

        getService().convertToTerminalDocument(nestedDocumentReference);

        ArgumentCaptor<MoveRequest> request = ArgumentCaptor.forClass(MoveRequest.class);
        verify(this.jobExecutor).execute(eq(RefactoringJobs.RENAME), request.capture());
        assertEquals(Arrays.asList(nestedDocumentReference), request.getValue().getEntityReferences());
        assertEquals(terminalDocumentReference, request.getValue().getDestination());

        assertNull(getService().convertToTerminalDocument(terminalDocumentReference));
        assertNull(getService().convertToTerminalDocument(rootDocumentReference));
    }

    private RefactoringScriptService getService() throws ComponentLookupException
    {
        return (RefactoringScriptService) this.mocker.getComponentUnderTest();
    }
}
