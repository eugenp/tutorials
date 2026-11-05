package com.baeldung.causeway.assets.domain;

import java.util.List;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.PriorityPrecedence;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.services.repository.RepositoryService;

@Named("assets.Assets")
@DomainService
@Priority(PriorityPrecedence.EARLY)
public class Assets {

    private final RepositoryService repositoryService;
    private final AssetRepository assetRepository;

    @Inject
    public Assets(
        final RepositoryService repositoryService,
        final AssetRepository assetRepository) {
        this.repositoryService = repositoryService;
        this.assetRepository = assetRepository;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public Asset create(
        @ParameterLayout(named = "Type") final AssetType type,
        @Parameter(maxLength = 80)
        @ParameterLayout(named = "Serial number") final String serialNumber) {
        return repositoryService.persist(new Asset(type, serialNumber.trim()));
    }

    @MemberSupport
    public String validate1Create(final String serialNumber) {
        if (serialNumber == null || serialNumber.isBlank()) {
            return "Serial number is required";
        }
        return assetRepository.findBySerialNumberIgnoreCase(serialNumber.trim()).isPresent()
            ? "An asset with this serial number already exists"
            : null;
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Asset> findBySerialNumber(
        @Parameter(maxLength = 80)
        @ParameterLayout(named = "Serial number") final String serialNumber) {
        return assetRepository.findBySerialNumberContainingIgnoreCaseOrderBySerialNumberAsc(
            serialNumber.trim()
        );
    }

    @Action(semantics = SemanticsOf.SAFE)
    public List<Asset> listAll() {
        return assetRepository.findAllByOrderBySerialNumberAsc();
    }
}
