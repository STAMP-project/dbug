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
package org.xwiki.officeimporter.script;

import java.io.InputStream;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.artofsolving.jodconverter.document.DocumentFamily;
import org.artofsolving.jodconverter.document.DocumentFormat;
import org.slf4j.Logger;
import org.xwiki.bridge.DocumentAccessBridge;
import org.xwiki.component.annotation.Component;
import org.xwiki.context.Execution;
import org.xwiki.model.reference.AttachmentReference;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.DocumentReferenceResolver;
import org.xwiki.officeimporter.OfficeImporterException;
import org.xwiki.officeimporter.builder.PresentationBuilder;
import org.xwiki.officeimporter.builder.XDOMOfficeDocumentBuilder;
import org.xwiki.officeimporter.builder.XHTMLOfficeDocumentBuilder;
import org.xwiki.officeimporter.converter.OfficeConverter;
import org.xwiki.officeimporter.document.XDOMOfficeDocument;
import org.xwiki.officeimporter.document.XHTMLOfficeDocument;
import org.xwiki.officeimporter.server.OfficeServer;
import org.xwiki.officeimporter.server.OfficeServer.ServerState;
import org.xwiki.officeimporter.server.OfficeServerConfiguration;
import org.xwiki.officeimporter.server.OfficeServerException;
import org.xwiki.officeimporter.splitter.TargetDocumentDescriptor;
import org.xwiki.officeimporter.splitter.XDOMOfficeDocumentSplitter;
import org.xwiki.script.service.ScriptService;

/**
 * Exposes the office importer APIs to server-side scripts.
 * 
 * @version $Id: 1e2046b4bc415f95c2c1fc35d4a259fbb2883cdb $
 * @since 4.1M1
 */
@Component
@Named("officeimporter")
@Singleton
public class OfficeImporterScriptService implements ScriptService
{
    /**
     * The key used to place any error messages while importing office documents.
     */
    public static final String OFFICE_IMPORTER_ERROR = "OFFICE_IMPORTER_ERROR";

    /**
     * The object used to log messages.
     */
    @Inject
    private Logger logger;

    /**
     * The {@link Execution} component.
     */
    @Inject
    private Execution execution;

    /**
     * The {@link DocumentAccessBridge} component.
     */
    @Inject
    private DocumentAccessBridge docBridge;

    /**
     * Used for converting string document names to objects.
     */
    @Inject
    @Named("currentmixed")
    private DocumentReferenceResolver<String> currentMixedDocumentReferenceResolver;

    /**
     * Used to query office server status.
     */
    @Inject
    private OfficeServer officeServer;

    /**
     * Used to query the office server configuration.
     */
    @Inject
    private OfficeServerConfiguration officeServerConfiguration;

    /**
     * Used for building {@link XHTMLOfficeDocument} instances from office documents.
     */
    @Inject
    private XHTMLOfficeDocumentBuilder xhtmlBuilder;

    /**
     * Used for building {@link XDOMOfficeDocument} instances from office documents.
     */
    @Inject
    private XDOMOfficeDocumentBuilder xdomBuilder;

    /**
     * Used for building {@link XDOMOfficeDocument} instances from office presentations.
     */
    @Inject
    private PresentationBuilder presentationBuilder;

    /**
     * Used to split {@link XDOMOfficeDocument} documents.
     */
    @Inject
    private XDOMOfficeDocumentSplitter xdomSplitter;

    /**
     * Imports the given office document into an {@link XHTMLOfficeDocument}.
     * 
     * @param officeFileStream binary data stream corresponding to input office document
     * @param officeFileName name of the input office document, this argument is mainly used for determining input
     *            document format where necessary
     * @param targetDocumentReference the document the import process is carried out relative to; this argument affects
     *            the attachment URLs generated during the import process where all references to attachments will be
     *            calculated assuming that the attachments are contained on the specified target document
     * @param filterStyles whether to filter styling information associated with the office document's content or not
     * @return {@link XHTMLOfficeDocument} containing xhtml result of the import operation or null if an error occurs
     * @since 4.3M1
     */
    public XHTMLOfficeDocument officeToXHTML(InputStream officeFileStream, String officeFileName,
        DocumentReference targetDocumentReference, boolean filterStyles)
    {
        try {
            assertConnected();
            return this.xhtmlBuilder.build(officeFileStream, officeFileName, targetDocumentReference, filterStyles);
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Imports the given office document into an {@link XHTMLOfficeDocument}.
     * 
     * @param officeFileStream binary data stream corresponding to input office document
     * @param officeFileName name of the input office document, this argument is mainly used for determining input
     *            document format where necessary
     * @param referenceDocument reference wiki document w.r.t which import process is carried out; this argument affects
     *            the attachment URLs generated during the import process where all references to attachments will be
     *            calculated assuming that the attachments are contained on the reference document
     * @param filterStyles whether to filter styling information associated with the office document's content or not
     * @return {@link XHTMLOfficeDocument} containing xhtml result of the import operation or null if an error occurs
     * @since 2.2M1
     * @deprecated use {@link #officeToXHTML(InputStream, String, DocumentReference, boolean)} instead
     */
    public XHTMLOfficeDocument officeToXHTML(InputStream officeFileStream, String officeFileName,
        String referenceDocument, boolean filterStyles)
    {
        return officeToXHTML(officeFileStream, officeFileName,
            this.currentMixedDocumentReferenceResolver.resolve(referenceDocument), filterStyles);
    }

    /**
     * Imports the given {@link XHTMLOfficeDocument} into an {@link XDOMOfficeDocument}.
     * 
     * @param xhtmlOfficeDocument {@link XHTMLOfficeDocument} to be imported
     * @return {@link XDOMOfficeDocument} containing {@link org.xwiki.rendering.block.XDOM} result of the import
     *         operation or null if an error occurs
     */
    public XDOMOfficeDocument xhtmlToXDOM(XHTMLOfficeDocument xhtmlOfficeDocument)
    {
        try {
            return this.xdomBuilder.build(xhtmlOfficeDocument);
        } catch (OfficeImporterException ex) {
            setErrorMessage(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Imports the given office document into an {@link XDOMOfficeDocument}.
     * 
     * @param officeFileStream binary data stream corresponding to input office document
     * @param officeFileName name of the input office document, this argument is mainly is used for determining input
     *            document format where necessary
     * @param targetDocumentReference the document the import process is carried out relative to; this argument affects
     *            the attachment URLs generated during the import process where all references to attachments will be
     *            calculated assuming that the attachments are contained on the specified target document
     * @param filterStyles whether to filter styling information associated with the office document's content or not
     * @return {@link XDOMOfficeDocument} containing {@link org.xwiki.rendering.block.XDOM} result of the import
     *         operation or null if an error occurs
     * @since 4.3M1
     */
    public XDOMOfficeDocument officeToXDOM(InputStream officeFileStream, String officeFileName,
        DocumentReference targetDocumentReference, boolean filterStyles)
    {
        try {
            assertConnected();
            if (isPresentation(officeFileName)) {
                return this.presentationBuilder.build(officeFileStream, officeFileName, targetDocumentReference);
            } else {
                return this.xdomBuilder.build(officeFileStream, officeFileName, targetDocumentReference, filterStyles);
            }
        } catch (Exception ex) {
            setErrorMessage(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Imports the given office document into an {@link XDOMOfficeDocument}.
     * 
     * @param officeFileStream binary data stream corresponding to input office document
     * @param officeFileName name of the input office document, this argument is mainly is used for determining input
     *            document format where necessary
     * @param referenceDocument reference wiki document w.r.t which import process is carried out; this srgument affects
     *            the attachment URLs generated during the import process where all references to attachments will be
     *            calculated assuming that the attachments are contained on the reference document
     * @param filterStyles whether to filter styling information associated with the office document's content or not
     * @return {@link XDOMOfficeDocument} containing {@link org.xwiki.rendering.block.XDOM} result of the import
     *         operation or null if an error occurs
     * @deprecated use {@link #officeToXDOM(InputStream, String, DocumentReference, boolean)} instead
     */
    public XDOMOfficeDocument officeToXDOM(InputStream officeFileStream, String officeFileName,
        String referenceDocument, boolean filterStyles)
    {
        return officeToXDOM(officeFileStream, officeFileName,
            this.currentMixedDocumentReferenceResolver.resolve(referenceDocument), filterStyles);
    }

    /**
     * Splits the given {@link XDOMOfficeDocument} into multiple {@link XDOMOfficeDocument} instances according to the
     * specified criterion. This method is useful when a single office document has to be imported and split into
     * multiple wiki pages. An auto generated TOC structure will be returned associated to <b>rootDocumentName</b>
     * {@link org.xwiki.officeimporter.splitter.TargetDocumentDescriptor} entry.
     * 
     * @param xdomDocument {@link XDOMOfficeDocument} to be split
     * @param headingLevels heading levels defining the split points on the original document
     * @param namingCriterionHint hint indicating the child pages naming criterion
     * @param rootDocumentReference the reference of the root document w.r.t which splitting will occur; in the results
     *            set the entry corresponding to the <b>root document</b> {@link TargetDocumentDescriptor} will hold an
     *            auto-generated TOC structure
     * @return a map holding {@link XDOMOfficeDocument} fragments against corresponding {@link TargetDocumentDescriptor}
     *         instances or null if an error occurs
     * @since 4.3M1
     */
    public Map<TargetDocumentDescriptor, XDOMOfficeDocument> split(XDOMOfficeDocument xdomDocument,
        String[] headingLevels, String namingCriterionHint, DocumentReference rootDocumentReference)
    {
        int[] splitLevels = new int[headingLevels.length];
        for (int i = 0; i < headingLevels.length; i++) {
            splitLevels[i] = Integer.parseInt(headingLevels[i]);
        }
        try {
            return this.xdomSplitter.split(xdomDocument, splitLevels, namingCriterionHint, rootDocumentReference);
        } catch (OfficeImporterException ex) {
            setErrorMessage(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Splits the given {@link XDOMOfficeDocument} into multiple {@link XDOMOfficeDocument} instances according to the
     * specified criterion. This method is useful when a single office document has to be imported and split into
     * multiple wiki pages. An auto generated TOC structure will be returned associated to <b>rootDocumentName</b>
     * {@link org.xwiki.officeimporter.splitter.TargetDocumentDescriptor} entry.
     * 
     * @param xdomDocument {@link XDOMOfficeDocument} to be split
     * @param headingLevels heading levels defining the split points on the original document
     * @param namingCriterionHint hint indicating the child pages naming criterion
     * @param rootDocumentName name of the root document w.r.t which splitting will occur; in the results set the entry
     *            corresponding to <b>rootDocumentName</b> {@link TargetDocumentDescriptor} will hold an auto-generated
     *            TOC structure
     * @return a map holding {@link XDOMOfficeDocument} fragments against corresponding {@link TargetDocumentDescriptor}
     *         instances or null if an error occurs
     * @deprecated use {@link #split(XDOMOfficeDocument, String[], String, DocumentReference)} instead
     */
    public Map<TargetDocumentDescriptor, XDOMOfficeDocument> split(XDOMOfficeDocument xdomDocument,
        String[] headingLevels, String namingCriterionHint, String rootDocumentName)
    {
        return split(xdomDocument, headingLevels, namingCriterionHint,
            this.currentMixedDocumentReferenceResolver.resolve(rootDocumentName));
    }

    /**
     * Attempts to save the given {@link XDOMOfficeDocument} into the target wiki page specified by arguments.
     * 
     * @param doc {@link XDOMOfficeDocument} to be saved
     * @param documentReference the reference of the target wiki page
     * @param syntaxId syntax of the target wiki page
     * @param parentReference the reference of the parent wiki page or {@code null}
     * @param title title of the target wiki page or {@code null}
     * @param append whether to append content if the target wiki page exists
     * @return true if the operation completes successfully, false otherwise
     * @since 4.3M1
     */
    public boolean save(XDOMOfficeDocument doc, DocumentReference documentReference, String syntaxId,
        DocumentReference parentReference, String title, boolean append)
    {
        try {
            // First check if the user has edit rights on the target document.
            if (!this.docBridge.isDocumentEditable(documentReference)) {
                String message = "You do not have edit rights on [%s] document.";
                throw new OfficeImporterException(String.format(message, documentReference));
            }

            // Save.
            if (this.docBridge.exists(documentReference) && append) {
                // Check whether existing document's syntax is same as target syntax.
                String currentSyntaxId = this.docBridge.getDocument(documentReference).getSyntax().toIdString();
                if (!currentSyntaxId.equals(syntaxId)) {
                    String message =
                        "The target page [%s] exists but its syntax [%s] is different from the specified syntax [%s]";
                    throw new OfficeImporterException(String.format(message, documentReference, currentSyntaxId,
                        syntaxId));
                }

                // Append the content.
                String currentContent = this.docBridge.getDocumentContent(documentReference, null);
                String newContent = currentContent + "\n" + doc.getContentAsString(syntaxId);
                this.docBridge.setDocumentContent(documentReference, newContent, "Updated by office importer.", false);
            } else {
                this.docBridge.setDocumentSyntaxId(documentReference, syntaxId);
                this.docBridge.setDocumentContent(documentReference, doc.getContentAsString(syntaxId),
                    "Created by office importer.", false);

                // Set parent if provided.
                if (null != parentReference) {
                    this.docBridge.setDocumentParentReference(documentReference, parentReference);
                }

                // If no title is specified, try to extract one.
                String docTitle = (null == title) ? doc.getTitle() : title;

                // Set title if applicable.
                if (null != docTitle) {
                    this.docBridge.setDocumentTitle(documentReference, docTitle);
                }
            }

            // Finally attach all the artifacts into target document.
            attachArtifacts(doc.getArtifacts(), documentReference);

            return true;
        } catch (OfficeImporterException ex) {
            setErrorMessage(ex.getMessage());
            logger.error(ex.getMessage(), ex);
        } catch (Exception ex) {
            String message = "Error while saving document [%s].";
            message = String.format(message, documentReference);
            setErrorMessage(message);
            logger.error(message, ex);
        }
        return false;
    }

    /**
     * Attempts to save the given {@link XDOMOfficeDocument} into the target wiki page specified by arguments.
     * 
     * @param doc {@link XDOMOfficeDocument} to be saved
     * @param target name of the target wiki page
     * @param syntaxId syntax of the target wiki page
     * @param parent name of the parent wiki page or null
     * @param title title of the target wiki page or null
     * @param append whether to append content if the target wiki page exists
     * @return true if the operation completes successfully, false otherwise
     * @deprecated use {@link #save(XDOMOfficeDocument, DocumentReference, String, DocumentReference, String, boolean)}
     *             instead
     */
    public boolean save(XDOMOfficeDocument doc, String target, String syntaxId, String parent, String title,
        boolean append)
    {
        return save(doc, this.currentMixedDocumentReferenceResolver.resolve(target), syntaxId,
            this.currentMixedDocumentReferenceResolver.resolve(parent), title, append);
    }

    /**
     * @return an error message set inside current execution (during import process) or null
     */
    public String getErrorMessage()
    {
        return (String) this.execution.getContext().getProperty(OFFICE_IMPORTER_ERROR);
    }

    /**
     * Utility method for setting an error message inside current execution.
     * 
     * @param message error message
     */
    private void setErrorMessage(String message)
    {
        this.execution.getContext().setProperty(OFFICE_IMPORTER_ERROR, message);
    }

    /**
     * Checks if the connection to the office server has been established. If the office server has been configured to
     * start automatically then we make an attempt to start it (usually this means that the office server failed to
     * start when XE started and so we try one more time to connect).
     * 
     * @throws OfficeImporterException if the connection to the office server is not established
     * @throws OfficeServerException if the attempt to start the office server failed
     */
    private void assertConnected() throws OfficeImporterException, OfficeServerException
    {
        boolean connected = this.officeServer.getState().equals(ServerState.CONNECTED);
        if (!connected) {
            // Check if the office server was configured to start automatically.
            if (this.officeServerConfiguration.isAutoStart()) {
                // The office server probably failed to start automatically when XE started. Try one more time to
                // connect.
                this.officeServer.start();
                connected = this.officeServer.getState().equals(ServerState.CONNECTED);
            }
            if (!connected) {
                throw new OfficeImporterException("Office server unavailable.");
            }
        }
    }

    /**
     * Utility method for checking if a file name corresponds to an office presentation.
     * 
     * @param officeFileName office file name
     * @return true if the file name / extension represents an office presentation format
     */
    private boolean isPresentation(String officeFileName)
    {
        String extension = officeFileName.substring(officeFileName.lastIndexOf('.') + 1);
        OfficeConverter officeConverter = officeServer.getConverter();
        if (officeConverter != null) {
            DocumentFormat format = officeConverter.getFormatRegistry().getFormatByExtension(extension);
            return format != null && format.getInputFamily() == DocumentFamily.PRESENTATION;
        }
        return false;
    }

    /**
     * Utility method for attaching artifacts into a wiki page.
     * 
     * @param artifacts map of artifact content against their names
     * @param targetDocumentReference target wiki page into which artifacts are to be attached
     */
    private void attachArtifacts(Map<String, byte[]> artifacts, DocumentReference targetDocumentReference)
    {
        for (Map.Entry<String, byte[]> artifact : artifacts.entrySet()) {
            AttachmentReference attachmentReference =
                new AttachmentReference(artifact.getKey(), targetDocumentReference);
            try {
                this.docBridge.setAttachmentContent(attachmentReference, artifact.getValue());
            } catch (Exception ex) {
                // Log the error and skip the artifact.
                logger.error("Error while attaching artifact.", ex);
            }
        }
    }
}
