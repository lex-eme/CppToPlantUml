#pragma once

#include "CoreMinimal.h"
#include "Hex.h"
#include "Tile.h"
#include "TileDescriptor.h"
#include "GameFramework/Actor.h"
#include "HexGrid.generated.h"

class AEnemy;
class AResource;
class ABuilding;
class AUnit;
class UMapData;
class UTextRenderComponent;

enum class ETileDebugType : uint8
{
	None,
	Cube,
	Offset,
	Weight,
	Probability,
};

class AHexGrid : public AActor
{
public:
	FVector TopLeftBound;
	FVector BottomRightBound;
	TArray<FHex> StartHexes;
	UMapData* Map;
	TArray<FHex> Path;
	int PathCost;

protected:
	float TileSize = 100.0f;

	UStaticMesh* GroundMesh;
	UMaterialInterface* GroundMaterial;

	UStaticMesh* GridMesh;
	UMaterialInterface* GridMaterial;

	TMap<FHex, FTile> Tiles;

	ETileDebugType DebugCoord = ETileDebugType::None;

	TSubclassOf<AResource> ResourceBlueprint;
	TSubclassOf<ABuilding> BuildingBlueprint;
	TSubclassOf<AUnit> UnitBlueprint;

private:
	UInstancedStaticMeshComponent* Ism;
	UInstancedStaticMeshComponent* GridIsm;

	TMap<FHex, UTextRenderComponent*> TextRenderComponents;
	int LastTotalWeight = 1;

public:
	AHexGrid();

	void SetMapData(UMapData* NewMapData);

	bool HasTile(FHex Hex) const;
	bool GetTile(FHex Hex, FTile& OutTile);
	void ComputeWeights();
	bool GetRandomWeightedTile(FHex& FoundHex);
	FHex GetHex(int32 Index) const;
	FHex GetHex(const FVector& Location) const;
	void GetNeighborHexes(FHex Hex, TArray<FHex>& OutNeighbors) const;
	TSet<FTile> GetNeighbors(const FTile& Tile);
	FVector GetTileLocation(FHex Hex) const;

	void AddTileState(FHex Hex, ETileState State);
	void RemoveTileState(FHex Hex, ETileState State);

	void AddEntity(AEntity* Entity, FHex To);
	void SendEntity(AUnit* Unit, FHex To);
	void MoveEntity(AUnit* Unit, FHex To);
	void RemoveEntity(FHex Location);

	void SpawnEnemy(FEnemyDescriptor Descriptor, AEnemy* Enemy);

	bool SearchPath(FHex From, FHex To);
	bool SearchCrossPath(FHex From, FHex To, int CrossLength);

	void Refresh();

protected:
	virtual void BeginPlay() override;
	virtual void OnConstruction(const FTransform& Transform) override;

	FOffsetCoord DebugFindTileCoord;
	FTile DebugTile;
	void DebugFindTile();


private:
	void SpawnTile(const FTileDescriptor& Descriptor);
	void ComputeBounds();
	void GetTileColor(FHex Hex, FLinearColor& Color);
	void UpdateTileColor(FHex Hex);
	static void SetIsmColor(UInstancedStaticMeshComponent* Ism, int Index, const FLinearColor& Color);
	TMap<FHex, int> TileWeights;
};
