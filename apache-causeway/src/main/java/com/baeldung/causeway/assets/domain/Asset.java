package com.baeldung.causeway.assets.domain;

import java.util.Comparator;

import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.annotation.Title;
import org.apache.causeway.applib.layout.LayoutConstants;
import org.apache.causeway.persistence.jpa.applib.integration.CausewayEntityListener;

@Entity
@Table(
    schema = "assets",
    name = "Asset",
    uniqueConstraints = @UniqueConstraint(
        name = "Asset__serialNumber__UNQ",
        columnNames = "serial_number"
    )
)
@EntityListeners(CausewayEntityListener.class)
@Named("assets.Asset")
@DomainObject
@DomainObjectLayout
public class Asset implements Comparable<Asset> {

    protected Asset() {
    }

    public Asset(final AssetType type, final String serialNumber) {
        this.type = type;
        this.serialNumber = serialNumber;
        this.status = AssetStatus.AVAILABLE;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private AssetType type;

    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.IDENTITY, sequence = "1")
    public AssetType getType() {
        return type;
    }

    @Column(name = "serial_number", nullable = false, length = 80)
    private String serialNumber;

    @Title(prepend = "Asset: ")
    @Property(maxLength = 80)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.IDENTITY, sequence = "2")
    public String getSerialNumber() {
        return serialNumber;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AssetStatus status;

    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "1")
    public AssetStatus getStatus() {
        return status;
    }

    @Column(name = "assigned_to", length = 100)
    private String assignedTo;

    @Property(optionality = Optionality.OPTIONAL, maxLength = 100)
    @PropertyLayout(fieldSetId = LayoutConstants.FieldSetId.DETAILS, sequence = "2")
    public String getAssignedTo() {
        return assignedTo;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout(
        fieldSetId = LayoutConstants.FieldSetId.DETAILS,
        position = ActionLayout.Position.PANEL,
        describedAs = "Assigns an available asset to an employee"
    )
    public Asset assignTo(
        @Parameter(maxLength = 100)
        @ParameterLayout(named = "Employee") final String employee) {
        assignedTo = employee.trim();
        status = AssetStatus.ASSIGNED;
        return this;
    }

    @MemberSupport
    public String disableAssignTo() {
        return status == AssetStatus.AVAILABLE
            ? null
            : "Only available assets can be assigned";
    }

    @MemberSupport
    public String validate0AssignTo(final String employee) {
        return employee == null || employee.isBlank()
            ? "Employee name is required"
            : null;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT)
    @ActionLayout(
        fieldSetId = LayoutConstants.FieldSetId.DETAILS,
        position = ActionLayout.Position.PANEL,
        describedAs = "Returns an assigned asset to inventory"
    )
    public Asset returnToInventory() {
        assignedTo = null;
        status = AssetStatus.AVAILABLE;
        return this;
    }

    @MemberSupport
    public String disableReturnToInventory() {
        return status == AssetStatus.ASSIGNED
            ? null
            : "Only assigned assets can be returned";
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
        fieldSetId = LayoutConstants.FieldSetId.DETAILS,
        position = ActionLayout.Position.PANEL,
        describedAs = "Permanently retires an asset"
    )
    public Asset retire() {
        assignedTo = null;
        status = AssetStatus.RETIRED;
        return this;
    }

    @MemberSupport
    public String disableRetire() {
        if (status == AssetStatus.ASSIGNED) {
            return "Return the asset before retiring it";
        }
        return status == AssetStatus.RETIRED
            ? "The asset is already retired"
            : null;
    }

    @Override
    public int compareTo(final Asset other) {
        return Comparator.comparing(Asset::getSerialNumber, String.CASE_INSENSITIVE_ORDER)
            .compare(this, other);
    }

    @Override
    public String toString() {
        return serialNumber;
    }
}
